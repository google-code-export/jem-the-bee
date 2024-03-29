/**
    JEM, the BEE - Job Entry Manager, the Batch Execution Environment
    Copyright (C) 2012-2015   Simone "Busy" Businaro
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
package org.pepstock.jem.node.executors.gfs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.pepstock.jem.gfs.GfsFileType;
import org.pepstock.jem.gfs.UploadedGfsChunkFile;
import org.pepstock.jem.log.LogAppl;
import org.pepstock.jem.node.NodeMessage;
import org.pepstock.jem.node.configuration.ConfigKeys;
import org.pepstock.jem.node.executors.DefaultExecutor;
import org.pepstock.jem.node.executors.ExecutorException;

/**
 * The executor upload a chunk of a file.
 * <br>
 * It writes the chunk contained inside of GFS file, passed as argument to the executor
 * 
 * @author Simone "Busy" Businaro
 * @version 1.2
 * 
 */
public class WriteChunk extends DefaultExecutor<Boolean> {

	private static final long serialVersionUID = 1L;

	private UploadedGfsChunkFile chunk;

	/**
	 * Write this chunk to the GFS
	 * 
	 * @param chunk chunk file to write on GFS
	 */
	public WriteChunk(UploadedGfsChunkFile chunk) {
		this.chunk = chunk;
	}

	/**
	 * @return the pathName
	 */
	public UploadedGfsChunkFile getChunk() {
		return chunk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.pepstock.jem.node.executors.DefaultExecutor#execute()
	 */
	@Override
	public final Boolean execute() throws ExecutorException {
		String parentPath = null;
		File file = null;
		// checks here the type of file-system to scan
		switch (chunk.getType()) {
			case GfsFileType.LIBRARY:
				// gets system property as parent
				parentPath = System.getProperty(ConfigKeys.JEM_LIBRARY_PATH_NAME);
				break;
			case GfsFileType.SOURCE:
				// gets system property as parent
				parentPath = System.getProperty(ConfigKeys.JEM_SOURCE_PATH_NAME);
				break;
			case GfsFileType.CLASS:
				// gets system property as parent
				parentPath = System.getProperty(ConfigKeys.JEM_CLASSPATH_PATH_NAME);
				break;
			case GfsFileType.BINARY:
				// gets system property as parent
				parentPath = System.getProperty(ConfigKeys.JEM_BINARY_PATH_NAME);
				break;
			default:
				// if here, the GFS type is wrong or not acceptable (like data path)
				throw new ExecutorException(NodeMessage.JEMC264E);
		}
		// a temporary file with parent path and chunk code
		file = new File(parentPath, chunk.getFilePath() + "." + chunk.getFileCode());
		try {
			// test the parent folder exists
			if (!file.getParentFile().exists()){
				FileUtils.forceMkdir(file.getParentFile());
			}
			// if tmp file does not exists create it with all directory structure
			if (!file.exists() && !file.createNewFile()){
				throw new ExecutorException(NodeMessage.JEMC266E, file.getAbsolutePath());
			}
			// if the transferred is complete just rename the tmp file
			if (chunk.isTransferComplete()) {
				File finalFile = new File(parentPath, chunk.getFilePath());
				// removes final file if exists
				if (finalFile.exists() && !finalFile.delete()){
					throw new ExecutorException(NodeMessage.JEMC268E, finalFile.getAbsolutePath());
				}
				// renames the uploaded file to final one
				if (!file.renameTo(finalFile)){
					throw new ExecutorException(NodeMessage.JEMC267E, file.getAbsolutePath(), finalFile.getAbsolutePath());
				}
				// updates also the last modified attribute
				if (!finalFile.setLastModified(chunk.getLastUpdate())){
					// if it can't, no prob. just a debug
					LogAppl.getInstance().debug("Unable to set last modified date! Ignored!");
				}
				return true;
			}
			// write to the temporary file in APPEND mode
			FileOutputStream output = new FileOutputStream(file.getAbsolutePath(), true);
			try {
				// write the buffer of chunkf
				output.write(chunk.getChunk(), 0, chunk.getNumByteToWrite());
			} finally {
				output.close();
			}
		} catch (Exception e) {
			// upload get an exception so delete tmp file
			try {
				FileUtils.deleteDirectory(file);
			} catch (IOException e1) {
				LogAppl.getInstance().ignore(e1.getMessage(), e1);
			}
			// throw the exception
			throw new ExecutorException(NodeMessage.JEMC265E, e, file.getAbsolutePath(), e.getMessage());
		}
		return true;
	}
}