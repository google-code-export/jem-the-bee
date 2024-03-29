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
package org.pepstock.jem.springbatch.tasks.utilities;

import org.pepstock.jem.springbatch.tasks.JemTasklet;
import org.pepstock.jem.springbatch.tasks.TaskletException;
import org.pepstock.jem.util.TimeUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Spring batch utility which wait for a specific amount of seconds.
 * 
 * @author Andrea "Stock" Stocchero
 * @version 1.0	
 *
 */
public class WaitTasklet extends JemTasklet {

	private int seconds = 0;
	
	/**
	 * Empty constructor
	 */
	public WaitTasklet() {
	}

	/* (non-Javadoc)
	 * @see org.pepstock.jem.springbatch.tasks.JemTasklet#run(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus run(StepContribution stepContribution, ChunkContext chuckContext) throws TaskletException {
		try {
			// creates an object to synchronized
			Object lock = new Object();
			synchronized (lock) {
				// if there is the number of seconds
				// wiats for those seconds
				if (seconds > 0){
					lock.wait(seconds * TimeUtils.SECOND);
				} else{
					// otherwise waits forever
					lock.wait();
				}
			}
		} catch (InterruptedException e) {
			throw new TaskletException(e.getMessage(), e);
		}
		return RepeatStatus.FINISHED;
	}

	/**
	 * @return the seconds
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * @param seconds the seconds to set
	 */
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
}