/**
    JEM, the BEE - Job Entry Manager, the Batch Execution Environment
    Copyright (C) 2012-2015   Andrea "Stock" Stocchero
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
package org.pepstock.jem.gwt.server.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pepstock.jem.Job;
import org.pepstock.jem.JobStatus;
import org.pepstock.jem.JobSystemActivity;
import org.pepstock.jem.OutputFileContent;
import org.pepstock.jem.OutputListItem;
import org.pepstock.jem.OutputTree;
import org.pepstock.jem.PreJob;
import org.pepstock.jem.gwt.server.UserInterfaceMessage;
import org.pepstock.jem.gwt.server.services.JobsManager;
import org.pepstock.jem.log.JemException;
import org.pepstock.jem.log.LogAppl;
import org.pepstock.jem.node.Queues;
import org.pepstock.jem.rest.entities.BooleanReturnedObject;
import org.pepstock.jem.rest.entities.JclContent;
import org.pepstock.jem.rest.entities.JobOutputFileContent;
import org.pepstock.jem.rest.entities.JobOutputListArgument;
import org.pepstock.jem.rest.entities.JobOutputTreeContent;
import org.pepstock.jem.rest.entities.JobStatusContent;
import org.pepstock.jem.rest.entities.JobSystemActivityContent;
import org.pepstock.jem.rest.entities.Jobs;
import org.pepstock.jem.rest.entities.StringReturnedObject;
import org.pepstock.jem.rest.paths.JobsManagerPaths;

/**
 * Rest service to manage jobs queues.<br>
 * Pay attention to URL (@path) annotation
 * 
 * @author Andrea "Stock" Stocchero
 *
 */
@Path(JobsManagerPaths.MAIN)
public class JobsManagerImpl extends DefaultServerResource  {

	private JobsManager jobsManager = null;
	
	/**
	 * REST service which returns jobs in input queue, by job name filter
	 * 
	 * @param jobNameFilter job name filter
	 * @return a jobs container
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.INPUT)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Jobs getInputQueue(String jobNameFilter) throws JemException {
		Jobs jobsContainer = new Jobs();
		if (isEnable()){
			if (jobsManager == null){
				initManager();
			}
			Collection<Job> jobs;
            try {
	            jobs = jobsManager.getInputQueue(jobNameFilter);
				jobsContainer.setJobs(jobs);
				jobsContainer.setQueueName(Queues.INPUT_QUEUE);	            
            } catch (Exception e) {
            	LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
            	jobsContainer.setExceptionMessage(e.getMessage());
            }				
		} else {
			setUnableExcepton(jobsContainer);
		}
		return jobsContainer;
	}

	/**
	 * REST service which returns jobs in running queue, by job name filter
	 * 
	 * @param jobNameFilter job name filter
	 * @return a jobs container
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.RUNNING)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Jobs getRunningQueue(String jobNameFilter) throws JemException {
		Jobs jobsContainer = new Jobs();
		if (isEnable()){
			if (jobsManager == null){
				initManager();
			}
			Collection<Job> jobs;
            try {
	            jobs = jobsManager.getRunningQueue(jobNameFilter);
				jobsContainer.setJobs(jobs);
				jobsContainer.setQueueName(Queues.RUNNING_QUEUE);	            
            } catch (Exception e) {
            	LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
            	jobsContainer.setExceptionMessage(e.getMessage());
            }				
		} else {
			setUnableExcepton(jobsContainer);
		}
		return jobsContainer;
	}

	/**
	 * REST service which returns jobs in output queue, by job name filter
	 * 
	 * @param jobNameFilter job name filter   
	 * @return a jobs container
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.OUTPUT)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Jobs getOutputQueue(String jobNameFilter) throws JemException {
		Jobs jobsContainer = new Jobs();
		if (isEnable()){
			if (jobsManager == null){
				initManager();
			}
			Collection<Job> jobs;
            try {
	            jobs = jobsManager.getOutputQueue(jobNameFilter);
				jobsContainer.setJobs(jobs);
				jobsContainer.setQueueName(Queues.OUTPUT_QUEUE);	            
            } catch (Exception e) {
            	LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
            	jobsContainer.setExceptionMessage(e.getMessage());
            }				
		} else {
			setUnableExcepton(jobsContainer);
		}
		return jobsContainer;
	}

	/**
	 * REST service which returns jobs in routing queue, by job name filter
	 * 
	 * @param jobNameFilter job name filter
	 * @return a jobs container
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.ROUTING)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Jobs getRoutingQueue(String jobNameFilter) throws JemException {
		Jobs jobsContainer = new Jobs();
		if (isEnable()){
			if (jobsManager == null){
				initManager();
			}
			Collection<Job> jobs;
            try {
	            jobs = jobsManager.getRoutingQueue(jobNameFilter);
				jobsContainer.setJobs(jobs);
				jobsContainer.setQueueName(Queues.ROUTING_QUEUE);	            
            } catch (Exception e) {
            	LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
            	jobsContainer.setExceptionMessage(e.getMessage());
            }				
		} else {
			setUnableExcepton(jobsContainer);
		}
		return jobsContainer;
	}

	/**
	 * REST service which returns jobs status, by job name filter
	 * 
	 * @param jobNameFilter job name filter
	 * @return a jobs container
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.JOB_STATUS)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobStatusContent getJobStatus(String jobNameFilter) throws JemException {
		JobStatusContent jobsContainer = new JobStatusContent();
		if (isEnable()){
			if (jobsManager == null){
				initManager();
			}
            try {
	            JobStatus jobStatus = jobsManager.getJobStatus(jobNameFilter);
				jobsContainer.setJobStatus(jobStatus);
            } catch (Exception e) {
            	LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
            	jobsContainer.setExceptionMessage(e.getMessage());
            }				
		} else {
			setUnableExcepton(jobsContainer);
		}
		return jobsContainer;
	}
	
	/**
	 * REST service which returns job, by job id filter
	 * 
	 * @param jobsParm job id filter
	 * @return a jobs container
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.JOB_BY_ID)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Jobs getJobById(Jobs jobsParm) throws JemException {
		Jobs jobsContainer = new Jobs();
		if (isEnable()){
			if (jobsParm.getQueueName() != null && jobsParm.getId() != null){
				if (jobsManager == null){
					initManager();
				}
				Collection<Job> jobs = new ArrayList<Job>();
				try {
					Job job = jobsManager.getJobById(jobsParm.getQueueName(), jobsParm.getId());
					if (job != null){
						jobs.add(job);
					}
					jobsContainer.setJobs(jobs);
					jobsContainer.setQueueName(jobsParm.getQueueName());	            
				} catch (Exception e) {
					LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
					jobsContainer.setExceptionMessage(e.getMessage());
				}				
			}
		} else {
			setUnableExcepton(jobsContainer);
		}
		return jobsContainer;
	}
	
	/**
	 * REST service which returns jobs ended by ID
	 * 
	 * @param jobIdFilter job id filter
	 * @return a jobs container
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.ENDED_JOB_BY_ID)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Jobs getEndedJobById(String jobIdFilter) throws JemException {
		Jobs jobsContainer = new Jobs();
		if (isEnable()){
			if (jobsManager == null){
				initManager();
			}
			Collection<Job> jobs = new ArrayList<Job>();
            try {
	            Job job = jobsManager.getEndedJobById(jobIdFilter);
	            if (job != null){
	            	jobs.add(job);
	            }	            
				jobsContainer.setJobs(jobs);
				jobsContainer.setQueueName(Queues.OUTPUT_QUEUE);	            
            } catch (Exception e) {
            	LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
            	jobsContainer.setExceptionMessage(e.getMessage());
            }				
		} else {
			setUnableExcepton(jobsContainer);
		}
		return jobsContainer;
	}
	
	/**
	 * Holds the jobs in a specific queue
	 * 
	 * @param jobs list and queue name of jobs to hold
	 * @return returns <code>true</code> if all jobs are changed, otherwise <code>false</code>
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.HOLD)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public BooleanReturnedObject hold(Jobs jobs) throws JemException {
		BooleanReturnedObject ro = new BooleanReturnedObject();
		ro.setValue(false);
		if (isEnable()){
			if (jobs.getQueueName() != null && !jobs.getJobs().isEmpty()){
				if (jobsManager == null){
					initManager();
				}
				try {
	                ro.setValue(jobsManager.hold(jobs.getJobs(), jobs.getQueueName()));
                } catch (Exception e) {
                	LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
                	ro.setExceptionMessage(e.getMessage());
                }
			}
		} else {
			setUnableExcepton(ro);
		}
		return ro;
	}

	/**
	 * Releases holded jobs in a specific queue
	 * 
	 * @param jobs list and queue name of jobs to release
	 * @return returns <code>true</code> if all jobs are changed, otherwise <code>false</code>
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.RELEASE)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public BooleanReturnedObject release(Jobs jobs) throws JemException {
		BooleanReturnedObject ro = new BooleanReturnedObject();
		ro.setValue(false);
		if (isEnable()) {
			if (jobs.getQueueName() != null && !jobs.getJobs().isEmpty()){
				if (jobsManager == null){
					initManager();
				}
				try {
					ro.setValue(jobsManager.release(jobs.getJobs(), jobs.getQueueName()));
				} catch (Exception e) {
					LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
					ro.setExceptionMessage(e.getMessage());
				}
			}
		} else {
			setUnableExcepton(ro);
		}
		return ro;
	}

	/**
	 * Cancels jobs in execution
	 * 
	 * @param jobs list and queue name of jobs to cancel
	 * @return returns <code>true</code> if all jobs are changed, otherwise <code>false</code>
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.CANCEL)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public BooleanReturnedObject cancel(Jobs jobs) throws JemException {
		BooleanReturnedObject ro = new BooleanReturnedObject();
		ro.setValue(false);
		if (isEnable()){
			if (!jobs.getJobs().isEmpty()){
				if (jobsManager == null){
					initManager();
				}
				try {
					ro.setValue(jobsManager.cancel(jobs.getJobs(), jobs.isCancelForce()));
				} catch (Exception e) {
					LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
					ro.setExceptionMessage(e.getMessage());
				}
			}
		} else {
			setUnableExcepton(ro);
		}
		return ro;
	}
	
	/**
	 * Purges jobs from queues
	 * 
	 * @param jobs list and queue name of jobs to purge
	 * @return returns <code>true</code> if all jobs are changed, otherwise <code>false</code>
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.PURGE)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public BooleanReturnedObject purge(Jobs jobs) throws JemException {
		BooleanReturnedObject ro = new BooleanReturnedObject();
		ro.setValue(false);
		if (isEnable()){
			if (jobs.getQueueName() != null && !jobs.getJobs().isEmpty()){
				if (jobsManager == null){
					initManager();
				}
				try {
					ro.setValue(jobsManager.purge(jobs.getJobs(), jobs.getQueueName()));
				} catch (Exception e) {
					LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
					ro.setExceptionMessage(e.getMessage());
				}
			}
		} else {
			setUnableExcepton(ro);
		}
		return ro;
	}
	
	/**
	 * Updates a job in queue
	 * 
	 * @param jobs list and queue name of jobs to update
	 * @return returns <code>true</code> if all jobs are changed, otherwise <code>false</code>
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.UPDATE)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public BooleanReturnedObject update(Jobs jobs) throws JemException {
		BooleanReturnedObject ro = new BooleanReturnedObject();
		ro.setValue(false);
		// only if there is a job and only 1
		if (isEnable()){
			if (jobs.getQueueName() != null && jobs.getJobs().size() == 1){
				if (jobsManager == null){
					initManager();
				}
				try {
					ro.setValue(jobsManager.update(jobs.getJobs().iterator().next(), jobs.getQueueName()));
				} catch (Exception e) {
					LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
					ro.setExceptionMessage(e.getMessage());
				}
			}
		} else {
			setUnableExcepton(ro);
		}
		return ro;
	}
	
	/**
	 * Submits a job in JEM, returning the job id
	 * 
	 * @param preJob job to submit 
	 * @return job ID calculated after submission
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.SUBMIT)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public StringReturnedObject submit(PreJob preJob) throws JemException {
		StringReturnedObject jobid = new StringReturnedObject();
		if (isEnable()){
			if (jobsManager == null){
				initManager();
			}
			try {
				String id = jobsManager.submit(preJob);
				jobid.setValue(id);
			} catch (Exception e) {
				LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
				jobid.setExceptionMessage(e.getMessage());
			}			
		} else {
			setUnableExcepton(jobid);
		}
		return jobid;
	}
	
	/**
	 * Returns the tree with all output files produced by job
	 * 
	 * @param jobs job container with queue name
	 * @return a tree with all references to output files
	 * @throws JemException if JEM group is not available or not authorized 
	 */
	@POST
	@Path(JobsManagerPaths.OUTPUT_TREE)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobOutputTreeContent getOutputTree(Jobs jobs) throws JemException {
		JobOutputTreeContent content = new JobOutputTreeContent();
		
		if (isEnable()){
			if (jobs.getQueueName() != null && jobs.getJobs().size() == 1){
				if (jobsManager == null){
					initManager();
				}
				try {
					Job job = jobs.getJobs().iterator().next();
					OutputTree tree = jobsManager.getOutputTree(job, jobs.getQueueName());
					content.setJclContent(tree.getJclContent());
					content.setFirstLevelItems(tree.getFirstLevelItems());
					content.setJob(job);
					for (List<OutputListItem> items : tree.getSecondLevelItems()){
						if (!items.isEmpty()){
							String key = items.get(0).getParent();
							content.getSecondLevelItems().put(key, items);
						}
					}
				} catch (Exception e) {
					LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
					content.setExceptionMessage(e.getMessage());
				}
			}
		} else {
			setUnableExcepton(content);
		}
		return content;
	}

	/**
	 * Returns the content of requested output file for a specific job.
	 * 
	 * @param jobFileContent container with output file and job instances
	 * @return content file
	 * @throws JemException if JEM group is not available or not authorized  
	 */
	@POST
	@Path(JobsManagerPaths.OUTPUT_FILE_CONTENT)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobOutputFileContent getOutputFileContent(JobOutputListArgument jobFileContent) throws JemException {
		JobOutputFileContent content = new JobOutputFileContent();
		if (isEnable()){
			if (jobsManager == null){
				initManager();
			}
			try {
	            OutputFileContent fileContent = jobsManager.getOutputFileContent(jobFileContent.getJob(), jobFileContent.getItem());
	            content.setJob(jobFileContent.getJob());
	            content.setOutputFileContent(fileContent);
            } catch (Exception e) {
            	LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
				content.setExceptionMessage(e.getMessage());
            }
		} else {
			setUnableExcepton(content);
		}
		return content;
	}

	/**
	 * Returns the content of requested jcl for a specific job.
	 * 
	 * @param jobs job container with queue name
	 * @return content JCL
	 * @throws JemException if JEM group is not available or not authorized  
	 */
	@POST
	@Path(JobsManagerPaths.JCL_CONTENT)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JclContent getJcl(Jobs jobs) throws JemException {
		JclContent content = new JclContent();
		if (isEnable()){
			if (jobs.getQueueName() != null && jobs.getJobs().size() == 1){
				if (jobsManager == null){
					initManager();
				}
				try {
					Job job = jobs.getJobs().iterator().next();
					String jclContent = jobsManager.getJcl(job, jobs.getQueueName());
					content.setContent(jclContent);
				} catch (Exception e) {
					LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
					content.setExceptionMessage(e.getMessage());
				}
			}
		} else {
			setUnableExcepton(content);
		}
		return content;
	}
	
	/**
	 * Returns the system information about the job in execution.
	 * 
	 * @param jobs job container with JOB IDS, process ids and members
	 * @return system information of job
	 * @throws JemException if JEM group is not available or not authorized  
	 */
	@POST
	@Path(JobsManagerPaths.JOB_SYSTEM_ACTIVITY)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public JobSystemActivityContent getJobSystemActivity(Jobs jobs) throws JemException {
		JobSystemActivityContent content = new JobSystemActivityContent();
		if (isEnable()){
			if (jobs.getJobs().size() == 1){
				if (jobsManager == null){
					initManager();
				}
				try {
					Job job = jobs.getJobs().iterator().next();
					JobSystemActivity jobContent = jobsManager.getJobSystemActivity(job);
					content.setJobSystemActivity(jobContent);
				} catch (Exception e) {
					LogAppl.getInstance().emit(UserInterfaceMessage.JEMG038E, e, e.getMessage());
					content.setExceptionMessage(e.getMessage());
				}
			}
		} else {
			setUnableExcepton(content);
		}
		return content;
	}	
	
	/**
	 * Initializes a jobs manager
	 */
	private synchronized void initManager(){
		if (jobsManager == null){
			jobsManager = new JobsManager();
		}
	}
}
