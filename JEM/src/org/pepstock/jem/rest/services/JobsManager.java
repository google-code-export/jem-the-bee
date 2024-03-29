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
package org.pepstock.jem.rest.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXBElement;

import org.pepstock.jem.Job;
import org.pepstock.jem.JobStatus;
import org.pepstock.jem.JobSystemActivity;
import org.pepstock.jem.OutputFileContent;
import org.pepstock.jem.OutputListItem;
import org.pepstock.jem.PreJob;
import org.pepstock.jem.log.JemException;
import org.pepstock.jem.rest.AbstractRestManager;
import org.pepstock.jem.rest.RestClient;
import org.pepstock.jem.rest.entities.BooleanReturnedObject;
import org.pepstock.jem.rest.entities.JclContent;
import org.pepstock.jem.rest.entities.JobOutputFileContent;
import org.pepstock.jem.rest.entities.JobOutputListArgument;
import org.pepstock.jem.rest.entities.JobOutputTreeContent;
import org.pepstock.jem.rest.entities.JobStatusContent;
import org.pepstock.jem.rest.entities.JobSystemActivityContent;
import org.pepstock.jem.rest.entities.Jobs;
import org.pepstock.jem.rest.entities.ReturnedObject;
import org.pepstock.jem.rest.entities.StringReturnedObject;
import org.pepstock.jem.rest.paths.JobsManagerPaths;

import com.sun.jersey.api.client.GenericType;

/**
 * REST Client side of JOBS service.
 * 
 * @author Andrea "Stock" Stocchero
 * 
 */
public class JobsManager extends AbstractRestManager {

	/**
	 * Creates a new REST manager using a RestClient
	 * 
	 * @param restClient
	 *            REST client instance
	 */
	public JobsManager(RestClient restClient) {
		super(restClient);
	}

	/**
	 * Returns the list of jobs in INPUT, a filter string composed by UI filters
	 * 
	 * @param jobNameFilter
	 *            filter string
	 * @return collection of jobs
	 * @throws JemException if any exception occurs
	 */
	public Jobs getInputQueue(String jobNameFilter) throws JemException {
		return getJobs(JobsManagerPaths.INPUT, jobNameFilter);
	}

	/**
	 * Returns the list of jobs in RUNNING, a filter string composed by UI
	 * filters
	 * 
	 * @param jobNameFilter
	 *            filter string
	 * @return collection of jobs
	 * @throws JemException if any exception occurs
	 */
	public Jobs getRunningQueue(String jobNameFilter) throws JemException {
		return getJobs(JobsManagerPaths.RUNNING, jobNameFilter);
	}

	/**
	 * Returns the list of jobs in OUTPUT, a filter string composed by UI
	 * filters
	 * 
	 * @param jobNameFilter
	 *            filter string
	 * @return collection of jobs
	 * @throws JemException if any exception occurs
	 */
	public Jobs getOutputQueue(String jobNameFilter) throws JemException {
		return getJobs(JobsManagerPaths.OUTPUT, jobNameFilter);
	}

	/**
	 * Returns the list of jobs in ROUTING, a filter string composed by UI
	 * filters
	 * 
	 * @param jobNameFilter
	 *            filter string
	 * @return collection of jobs
	 * @throws JemException if any exception occurs
	 */
	public Jobs getRoutingQueue(String jobNameFilter) throws JemException {
		return getJobs(JobsManagerPaths.ROUTING, jobNameFilter);
	}

	/**
	 * Holds jobs in INPUT, OUTPUT or ROUTING queue. To set HOLD means that it
	 * can't be executed or removed from queue.
	 * 
	 * @param jobs
	 *            collections of jobs to hold
	 * @param queueName
	 *            map where jobs are
	 * @return true is it holds them, otherwise false
	 * @throws JemException if any exception occurs
	 */
	public Boolean hold(Collection<Job> jobs, String queueName) throws JemException {
		JobsPostService<BooleanReturnedObject, Jobs> service = new JobsPostService<BooleanReturnedObject, Jobs>(JobsManagerPaths.HOLD);
		GenericType<JAXBElement<BooleanReturnedObject>> generic = new GenericType<JAXBElement<BooleanReturnedObject>>() {

		};
		Jobs jobsEnvelop = new Jobs();
		jobsEnvelop.setJobs(jobs);
		jobsEnvelop.setQueueName(queueName);
		
		BooleanReturnedObject result = service.execute(generic, jobsEnvelop);
		if (result == null){
			return false;
		}
		return result.isValue();
	}

	/**
	 * Releases jobs in INPUT, OUTPUT or ROUTING queue, which were previously
	 * hold.
	 * 
	 * @param jobs
	 *            collections of jobs to hold
	 * @param queueName
	 *            map where jobs are
	 * @return true is it holds them, otherwise false
	 * @throws JemException if any exception occurs
	 */
	public Boolean release(Collection<Job> jobs, String queueName) throws JemException {
		JobsPostService<BooleanReturnedObject, Jobs> service = new JobsPostService<BooleanReturnedObject, Jobs>(JobsManagerPaths.RELEASE);
		GenericType<JAXBElement<BooleanReturnedObject>> generic = new GenericType<JAXBElement<BooleanReturnedObject>>() {

		};
		Jobs jobsEnvelop = new Jobs();
		jobsEnvelop.setJobs(jobs);
		jobsEnvelop.setQueueName(queueName);
		
		BooleanReturnedObject result = service.execute(generic, jobsEnvelop);
		if (result == null){
			return false;
		}
		return result.isValue();
	}

	/**
	 * Cancel a set of jobs currently in running. If force is set to true, JEM
	 * uses force mode to cancel jobs.
	 * 
	 * @param jobs
	 *            list of jobs to cancel
	 * @param force
	 *            if true, uses force attribute to cancel jobs
	 * @return always true!
	 * @throws JemException if any exception occurs
	 */
	public Boolean cancel(Collection<Job> jobs, boolean force) throws JemException {
		JobsPostService<BooleanReturnedObject, Jobs> service = new JobsPostService<BooleanReturnedObject, Jobs>(JobsManagerPaths.CANCEL);
		GenericType<JAXBElement<BooleanReturnedObject>> generic = new GenericType<JAXBElement<BooleanReturnedObject>>() {

		};
		Jobs jobsEnvelop = new Jobs();
		jobsEnvelop.setJobs(jobs);
		jobsEnvelop.setCancelForce(force);
		
		BooleanReturnedObject result = service.execute(generic, jobsEnvelop);
		if (result == null){
			return false;
		}
		return result.isValue();
	}	

	/**
	 * Purge (removing any output) jobs from INPUT, OUTPUT or ROUTING queue.
	 * 
	 * @param jobs
	 *            collections of jobs to purge
	 * @param queueName
	 *            map where jobs are
	 * @return true is it holds them, otherwise false
	 * @throws JemException if any exception occurs
	 */
	public Boolean purge(Collection<Job> jobs, String queueName) throws JemException {
		JobsPostService<BooleanReturnedObject, Jobs> service = new JobsPostService<BooleanReturnedObject, Jobs>(JobsManagerPaths.PURGE);
		GenericType<JAXBElement<BooleanReturnedObject>> generic = new GenericType<JAXBElement<BooleanReturnedObject>>() {

		};
		Jobs jobsEnvelop = new Jobs();
		jobsEnvelop.setJobs(jobs);
		jobsEnvelop.setQueueName(queueName);
		
		BooleanReturnedObject result = service.execute(generic, jobsEnvelop);
		if (result == null){
			return false;
		}
		return result.isValue();
	}
	
	/**
	 * Updates some attributes of job. Usually is used in input queue to change
	 * environment, domain, affinity, memory or priority.
	 * 
	 * @param job
	 *            job to update
	 * @param queueName
	 *            map where job is
	 * @return true if it updated, otherwise false
	 * @throws JemException if any exception occurs
	 */
	public Boolean update(Job job, String queueName) throws JemException {
		JobsPostService<BooleanReturnedObject, Jobs> service = new JobsPostService<BooleanReturnedObject, Jobs>(JobsManagerPaths.UPDATE);
		GenericType<JAXBElement<BooleanReturnedObject>> generic = new GenericType<JAXBElement<BooleanReturnedObject>>() {

		};
		Collection<Job> jobs = new ArrayList<Job>();
		jobs.add(job);

		Jobs jobsEnvelop = new Jobs();
		jobsEnvelop.setJobs(jobs);
		jobsEnvelop.setQueueName(queueName);
		
		BooleanReturnedObject result = service.execute(generic, jobsEnvelop);
		if (result == null){
			return false;
		}
		return result.isValue();
	}	

	/**
	 * Submits a new job (passed as argument) in JEM.
	 * 
	 * @param preJob
	 *            job to submit
	 * @return Job id after submission
	 * @throws JemException if any exception occurs
	 */
	public String submit(PreJob preJob) throws JemException {
		GenericType<JAXBElement<StringReturnedObject>> generic = new GenericType<JAXBElement<StringReturnedObject>>() {

		};
		JobsPostService<StringReturnedObject, PreJob> service = new JobsPostService<StringReturnedObject, PreJob>(JobsManagerPaths.SUBMIT);

		StringReturnedObject jobid = service.execute(generic, preJob);
		if (jobid == null){
			return null;
		} else {
			return jobid.getValue();
		}
	}

	/**
	 * Retrieves job JCL from INPUT, RUNNING, OUTPUT or ROUTING queue.
	 * 
	 * @param job
	 *            job used to extract JCl
	 * @param queueName
	 *            map where job is
	 * @return JCL content
	 * @throws JemException if any exception occurs
	 */
	public String getJcl(Job job, String queueName) throws JemException {
		GenericType<JAXBElement<JclContent>> generic = new GenericType<JAXBElement<JclContent>>() {

		};
		Collection<Job> jobs = new ArrayList<Job>();
		jobs.add(job);

		Jobs jobsEnvelop = new Jobs();
		jobsEnvelop.setJobs(jobs);
		jobsEnvelop.setQueueName(queueName);
		
		JobsPostService<JclContent, Jobs> service = new JobsPostService<JclContent, Jobs>(JobsManagerPaths.JCL_CONTENT);
		JclContent result = service.execute(generic, jobsEnvelop);
		if (result == null){
			return null;
		}
		return result.getContent();
	}

	/**
	 * Returns all folder in tree representation of job output folder.<br>
	 * Retrieves folder structure only if job is RUNNING or OUTPUT queue.
	 * 
	 * @param job
	 *            job used to extract folder structure. Needs to have output
	 *            folder
	 * @param queueName
	 *            map where job is
	 * @return object with all folder structure
	 * @throws JemException if any exception occurs
	 */
	public JobOutputTreeContent getOutputTree(Job job, String queueName) throws JemException {
		JobsPostService<JobOutputTreeContent, Jobs> service = new JobsPostService<JobOutputTreeContent, Jobs>(JobsManagerPaths.OUTPUT_TREE);
		GenericType<JAXBElement<JobOutputTreeContent>> generic = new GenericType<JAXBElement<JobOutputTreeContent>>() {

		};
		Collection<Job> jobs = new ArrayList<Job>();
		jobs.add(job);

		Jobs jobsEnvelop = new Jobs();
		jobsEnvelop.setJobs(jobs);
		jobsEnvelop.setQueueName(queueName);
		
		return service.execute(generic, jobsEnvelop);
	}

	/**
	 * Returns the content of specific file inside of job output folder.
	 * 
	 * @param job
	 *            job used to extract file. Needs to have output folder
	 * @param item
	 *            file descriptor, created by a previous call to getOutputTree
	 * @return object with file content
	 * @throws JemException if any exception occurs
	 */
	public OutputFileContent getOutputFileContent(Job job, OutputListItem item) throws JemException {
		GenericType<JAXBElement<JobOutputFileContent>> generic = new GenericType<JAXBElement<JobOutputFileContent>>() {

		};
		JobOutputListArgument jobFileContent = new JobOutputListArgument();
		jobFileContent.setJob(job);
		jobFileContent.setItem(item);
		
		JobsPostService<JobOutputFileContent, JobOutputListArgument> service = new JobsPostService<JobOutputFileContent, JobOutputListArgument>(JobsManagerPaths.OUTPUT_FILE_CONTENT);
		JobOutputFileContent ofc = service.execute(generic, jobFileContent);
		
		if (ofc == null){
			return null;
		} else {
			return ofc.getOutputFileContent();
		}
	}

	/**
	 * Returns a job status by filter.<br>
	 * Filter must be job name (no pattern with wild-cards) or job id
	 * 
	 * @param filter
	 *            job name (no pattern with wild-cards) or job id
	 * @return job status
	 * @throws JemException if any exception occurs
	 */
	public JobStatus getJobStatus(String filter) throws JemException {
		GenericType<JAXBElement<JobStatusContent>> generic = new GenericType<JAXBElement<JobStatusContent>>() {

		};
		JobsPostService<JobStatusContent, String> service = new JobsPostService<JobStatusContent, String>(JobsManagerPaths.JOB_STATUS);
		JobStatusContent sc = service.execute(generic, filter);
		
		if (sc == null){
			return null;
		} else {
			return sc.getJobStatus();
		}
	}
	
	/**
	 * Returns a job by its job id.
	 * 
	 * @param queueName
	 *            map name
	 * @param jobId
	 *            job id to search
	 * @return job, if found, otherwise null
	 * @throws JemException if any exception occurs
	 */
	public Job getJobById(String queueName, String jobId) throws JemException {
		JobsPostService<Jobs, Jobs> service = new JobsPostService<Jobs, Jobs>(JobsManagerPaths.JOB_BY_ID);
		GenericType<JAXBElement<Jobs>> generic = new GenericType<JAXBElement<Jobs>>() {

		};
		Jobs jobsEnvelop = new Jobs();
		jobsEnvelop.setId(jobId);
		jobsEnvelop.setQueueName(queueName);
		
		Jobs jobs = service.execute(generic, jobsEnvelop);
		if (jobs == null || jobs.getJobs() == null || jobs.getJobs().isEmpty()){
			return null;
		} else {
			return jobs.getJobs().iterator().next();
		}		
	}
	
	/**
	 * Returns a job by its job id, searching it in OUTPUT and ROUTED maps.<br>
	 * If job is not in output, tries searching it in ROUTED
	 * 
	 * @param jobId
	 *            job id
	 * @return job instance
	 * @throws JemException if any exception occurs
	 */
	public Job getEndedJobById(String jobId) throws JemException {
		JobsPostService<Jobs, String> service = new JobsPostService<Jobs, String>(JobsManagerPaths.ENDED_JOB_BY_ID);
		GenericType<JAXBElement<Jobs>> generic = new GenericType<JAXBElement<Jobs>>() {

		};
		Jobs jobs = service.execute(generic, jobId);
		if (jobs == null || jobs.getJobs() == null || jobs.getJobs().isEmpty()){
			return null;
		} else {
			return jobs.getJobs().iterator().next();
		}
	}
	
	/**
	 * Returns system information about resource consumption of job in running
	 * phase.
	 * 
	 * @param job
	 *            job to use to gather system information
	 * @return system activity information
	 * @throws JemException if any exception occurs
	 */
	public JobSystemActivity getJobSystemActivity(Job job) throws JemException {
		GenericType<JAXBElement<JobSystemActivityContent>> generic = new GenericType<JAXBElement<JobSystemActivityContent>>() {

		};
		Collection<Job> jobs = new ArrayList<Job>();
		jobs.add(job);

		Jobs jobsEnvelop = new Jobs();
		jobsEnvelop.setJobs(jobs);
		
		JobsPostService<JobSystemActivityContent, Jobs> service = new JobsPostService<JobSystemActivityContent, Jobs>(JobsManagerPaths.JOB_SYSTEM_ACTIVITY);
		JobSystemActivityContent sa = service.execute(generic, jobsEnvelop);
		
		if (sa == null){
			return null;
		} else {
			return sa.getJobSystemActivity();
		}
	}
	
	/**
	 * This is common method to extract jobs from different queues by filter
	 * string
	 * 
	 * @param method
	 *            queue name to use to get the right map
	 * @param filter
	 *            filter string
	 * @return collection of jobs
	 * @throws JemException if any exception occurs
	 */
	private Jobs getJobs(String method, String filter) throws JemException {
		JobsPostService<Jobs, String> service = new JobsPostService<Jobs, String>(method);
		GenericType<JAXBElement<Jobs>> generic = new GenericType<JAXBElement<Jobs>>() {

		};
		return service.execute(generic, filter);
	}
	
	/**
	 * Inner service, which extends post the default post service.
	 * @author Andrea "Stock" Stocchero
	 * @version 2.2
	 */
	class JobsPostService<T extends ReturnedObject, S> extends DefaultPostService<T, S> {

		/**
		 * Constructs the REST service, using HTTP client and service and subservice paths, passed as argument
		 * 
		 * @param subService subservice path
		 * 
		 */
		public JobsPostService(String subService) {
			super(JobsManager.this.getClient(), JobsManagerPaths.MAIN, subService);
		}

	}

}