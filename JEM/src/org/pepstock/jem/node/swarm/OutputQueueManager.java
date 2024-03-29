/**
    JEM, the BEE - Job Entry Manager, the Batch Execution Environment
    Copyright (C) 2012-2015  Simone "Busy" Businaro
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pepstock.jem.node.swarm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.pepstock.jem.Job;
import org.pepstock.jem.log.LogAppl;
import org.pepstock.jem.node.JobComparator;
import org.pepstock.jem.node.Main;
import org.pepstock.jem.node.NodeInfo;
import org.pepstock.jem.node.OutputQueuePredicate;
import org.pepstock.jem.node.Queues;
import org.pepstock.jem.node.Status;
import org.pepstock.jem.node.swarm.executors.RouterOut;

import com.hazelcast.core.DistributedTask;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;

/**
 * Output queue manager listeners, whcih informs when a job is ended (put in OUTPTU map).
 * 
 * @author Simone "Busy" Businaro
 * @version 1.3
 * 
 */
public class OutputQueueManager implements EntryListener<String, Job> {

	private JobComparator comparator = new JobComparator();

	private boolean notifyOutputEnded = true;

	/**
	 * Adds itself as listener to OUTPUT map
	 */
	public OutputQueueManager() {
		IMap<String, Job> routingQueue = Main.getHazelcast().getMap(Queues.OUTPUT_QUEUE);
		routingQueue.addEntryListener(this, true);
	}

	/* (non-Javadoc)
	 * @see com.hazelcast.core.EntryListener#entryAdded(com.hazelcast.core.EntryEvent)
	 */
	@Override
	public void entryAdded(EntryEvent<String, Job> event) {
		// if swarm is active
		if (Main.SWARM.getStatus().equals(Status.ACTIVE)) {
			Job job = event.getValue();
			// if it was a routed job notify the environment from where it was
			// routing that the job ended
			if (job.getRoutingInfo().getEnvironment() != null) {
				notifyEndedRoutedJob(job);
			}
		}		
	}

	/* (non-Javadoc)
	 * @see com.hazelcast.core.EntryListener#entryEvicted(com.hazelcast.core.EntryEvent)
	 */
	@Override
	public void entryEvicted(EntryEvent<String, Job> arg0) {
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see com.hazelcast.core.EntryListener#entryRemoved(com.hazelcast.core.EntryEvent)
	 */
	@Override
	public void entryRemoved(EntryEvent<String, Job> arg0) {
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see com.hazelcast.core.EntryListener#entryUpdated(com.hazelcast.core.EntryEvent)
	 */
	@Override
	public void entryUpdated(EntryEvent<String, Job> arg0) {
		// Do nothing		
	}

	/**
	 * This method will send back to the environment that execute the routing
	 * the routed jobs to notify their end.
	 */
	public synchronized void notifyEndedRoutedJobsByAvailableEnvironments() {
		// if swarm is active
		if (Main.SWARM.getStatus().equals(Status.ACTIVE)) {
			IMap<String, NodeInfo> nodesMap = Main.SWARM.getHazelcastInstance().getMap(SwarmQueues.NODES_MAP);
			Collection<NodeInfo> nodes = nodesMap.values();
			// notify ended job only if nodes exist
			if (nodes != null && !nodes.isEmpty()) {
				// set of active environments
				Set<String> environments = new HashSet<String>();
				Iterator<NodeInfo> nodesiter = nodes.iterator();
				// loads all swarm nodes (of other environment)
				while (nodesiter.hasNext()) {
					String currEnv = nodesiter.next().getExecutionEnvironment().getEnvironment();
					// look only for environments different from local one
					if (!currEnv.equalsIgnoreCase(Main.EXECUTION_ENVIRONMENT.getEnvironment())){
						environments.add(currEnv);
					}
				}
				// gets output queue
				IMap<String, Job> outputQueue = Main.getHazelcast().getMap(Queues.OUTPUT_QUEUE);
				// creates the predicate 
				// to get all jobs submitted from another environment
				OutputQueuePredicate oqp = new OutputQueuePredicate();
				oqp.setEnvironments(environments);
				// gets jobs
				Collection<Job> jobs = outputQueue.values(oqp);
				// sort jobs
				List<Job> queuedJobs = new ArrayList<Job>(jobs);
				Collections.sort(queuedJobs, comparator);
				// notifies the end of the job
				for (Job currJob : queuedJobs) {
					notifyEndedRoutedJob(currJob);
				}
			}
		}
	}

	/**
	 * Notify the end of a single job
	 * 
	 * @param currJob job ended and submitted from another environment
	 */
	private synchronized void notifyEndedRoutedJob(Job currJob) {
		// if swarm is active
		if (Main.SWARM.getStatus().equals(Status.ACTIVE)) {
			// sets status that is working
			setNotifyOutputEnded(false);
			IMap<String, Job> outputQueue = Main.getHazelcast().getMap(Queues.OUTPUT_QUEUE);
			IMap<String, NodeInfo> nodesMap = Main.SWARM.getHazelcastInstance().getMap(SwarmQueues.NODES_MAP);
			// lock the entry of the job
			try {
				// gets job by job id
				outputQueue.lock(currJob.getId());
				Job job = outputQueue.get(currJob.getId());
				// if job is still pending to be notified
				if (job != null && job.getRoutingInfo().isOutputCommitted() == null) {
					// gets member to reply the end of the job
					MapSwarmNodePredicate mnp = new MapSwarmNodePredicate();
					mnp.setEnvironment(job.getRoutingInfo().getEnvironment());
					Member member = MapSwarmNodesManager.getMember(nodesMap.values(mnp));
					// check if member is still available otherwise do
					// nothing
					if (member != null) {
						LogAppl.getInstance().emit(SwarmNodeMessage.JEMO006I, job);
						// executes a distrbuted task to notified to the member
						// previously extracted that
						// the job is ended
						DistributedTask<Boolean> task = new DistributedTask<Boolean>(new RouterOut(job), member);
						ExecutorService executorService = Main.SWARM.getHazelcastInstance().getExecutorService();
						// start 2 phase commit
						// setting the status to false to the job and saving it again
						job.getRoutingInfo().setOutputCommitted(false);
						outputQueue.put(job.getId(), job);
						executorService.execute(task);
						if (task.get()) {
							// now output is committed
							// and set the status
							job.getRoutingInfo().setOutputCommitted(true);
							// and save again
							outputQueue.put(job.getId(), job);
							LogAppl.getInstance().emit(SwarmNodeMessage.JEMO007I, job);
						}
					}
				}
			} catch (Exception e) {
				LogAppl.getInstance().emit(SwarmNodeMessage.JEMO005E, currJob, e);
			} finally {
				// always unloch output queue
				if (outputQueue != null) {
					outputQueue.unlock(currJob.getId());
				}
			}
			// reset nofication status
			setNotifyOutputEnded(true);
		}
	}

	/**
	 * @return the notifyOutputEnded used to see if the OutputQueueManager is
	 *         still working
	 */
	public boolean isNotifyOutputEnded() {
		return notifyOutputEnded;
	}

	/**
	 * @param notifyOutputEnded the notifyOutputEnded to set
	 */
	private void setNotifyOutputEnded(boolean notifyOutputEnded) {
		this.notifyOutputEnded = notifyOutputEnded;
	}
}