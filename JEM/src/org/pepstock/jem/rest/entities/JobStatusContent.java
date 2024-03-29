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
package org.pepstock.jem.rest.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.pepstock.jem.JobStatus;


/**
 * Represents job status content wrapper, necessary for rest calls.
 * 
 * @author Andrea "Stock" Stocchero
 * @version 2.2
 */
@XmlRootElement
public class JobStatusContent extends ReturnedObject implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private JobStatus jobStatus = null;

	/**
	 * Empty constructor
	 */
	public JobStatusContent() {
		
	}

	/**
	 * @return the jobStatus
	 */
	public JobStatus getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JobStatusContent [jobStatus=" + jobStatus + "]";
	}
}
