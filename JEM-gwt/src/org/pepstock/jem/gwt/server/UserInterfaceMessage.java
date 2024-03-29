/**
    JEM, the BEE - Job Entry Manager, the Batch Execution Environment
    Copyright (C) 2012-2015   Alessandro Zambrini
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
package org.pepstock.jem.gwt.server;

import org.pepstock.jem.log.Description;
import org.pepstock.jem.log.Message;
import org.pepstock.jem.log.MessageCode;
import org.pepstock.jem.log.MessageInterface;
import org.pepstock.jem.log.MessageLevel;
import org.pepstock.jem.node.configuration.ConfigKeys;

/**
 * It is an enumeration containing all the messages related to the user interface. <br>
 * It is a list of <code>UserInterfaceMessage</code>. <br> 
 * Each <code>UserInterfaceMessage</code> in the list corresponds to a <code>Message</code>. <br>
 * It implements {@link MessageInterface}
 * 
 * @see Message
 * @author Alessandro Zambrini
 * @version 1.0	
 */
public enum UserInterfaceMessage implements MessageInterface {
	
	/**
	 * "Executor call back exception.", MessageLevel.ERROR
	 */
	@Description(explanation = "Internal error during a command from user interface.")
	JEMG001E(1, "Executor call back exception.", MessageLevel.ERROR),

	/**
	 * "Executor call back result {0}.", MessageLevel.INFO
	 */
	@Description(explanation = "Internal error during a command from user interface.")
	JEMG002I(2, "Executor call back result {0}.", MessageLevel.INFO),
	
	/**
	 * "Group \"{0}\" is not available, no member is running. Please try again,
	 * MessageLevel.ERROR
	 */
	JEMG003E(3, "Group \"{0}\" is not available, no member is running. Please try again", MessageLevel.ERROR),

	/**
	 * "JEM Group {0} is available", MessageLevel.INFO
	 */
	JEMG004I(4, "JEM Group {0} is available", MessageLevel.INFO),

	/**
	 * "JEM Group {0} is unavailable", MessageLevel.ERROR
	 */
	JEMG005E(5, "JEM Group {0} is unavailable", MessageLevel.ERROR),

	/**
	 * "Hazelcast node is starting", MessageLevel.INFO
	 */
	JEMG006I(6, "Hazelcast node is starting", MessageLevel.INFO),

	/**
	 * Hazelcast node [{0}] started", MessageLevel.INFO
	 */
	@Description(explanation = "Display the UUID of Hazelcast member")
	JEMG007I(7, "Hazelcast node [{0}] started", MessageLevel.INFO),

	/**
	 * "Current user {0} doesn't have permission for {1}", MessageLevel.ERROR
	 */
	JEMG008E(8, "Current user {0} does not have permission for {1}", MessageLevel.ERROR),
	
	/**
	 * "Current user {0} is not authenticated", MessageLevel.ERROR
	 */
	JEMG009E(9, "Current user {0} is not authenticated", MessageLevel.ERROR),
	
	/**
	 * "Login for {0} is failed: Installation is needed", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when you are still in config phase. Installation is needed. Use default grantor id and at least add an administrator")
	JEMG010E(10, "Login for {0} is failed: Installation is needed", MessageLevel.ERROR),

	/**
	 * "There is no user with username of {0}.", MessageLevel.ERROR
	 */
	JEMG011E(11, "There is no user with username of {0}", MessageLevel.ERROR),

	/**
	 * "Unable to write statistics log record in \"{0}\" file", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when user is not authorized or authenticated to access to JEM.")
	JEMG012E(12, "Login failed", MessageLevel.ERROR),

	/**
	 * "Password for account {0} was incorrect!", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when user typed a wrong password.")
	JEMG013E(13, "Password for account {0} was incorrect", MessageLevel.ERROR),

	/**
	 * "The account for username {0} is locked", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when user is locked. Please contact your administrator to unlock it.")
	JEMG014E(14, "The account for username {0} is locked", MessageLevel.ERROR),
	
	/**
	 * "Login generic for {0} is failed: {1}", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when user wans't able to login JEM")
	JEMG015E(15, "Login for {0} is failed: {1}", MessageLevel.ERROR),
	
	/**
	 * "User {0} logged in successfully.", MessageLevel.INFO
	 */
	JEMG016I(16, "User {0} logged in successfully", MessageLevel.INFO),

	/**
	 * "User {0} logged off successfully.", MessageLevel.INFO
	 */
	JEMG017I(17, "User {0} logged off successfully", MessageLevel.INFO),

	/**
	 * "Unable to execute statistics remote command on all nodes", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when receives an exception during the execution of future task on all node to extract statistics.<br>Check the showed exception.")
	JEMG018E(18, "Unable to execute statistics remote command on all nodes", MessageLevel.ERROR),

	/**
	 * "This is the new installation!", MessageLevel.WARNING
	 */
	@Description(explanation = "It occurs when there is new installation and there's not any administrator.<br>Connet with default grantor and define an administrator, and then logoff.")
	JEMG019W(19, "This is the new installation", MessageLevel.WARNING),

	/**
	 * "Security framework is using {0} cache manager", MessageLevel.INFO
	 */
	@Description(explanation = "It informs about which kind of cache Apache Shiro is using. When is MEMORY, means that JEM cluster is down, otherwise uses Halzecast layer to retrievs permissions.")
	JEMG020I(20, "Security framework is using {0} cache manager", MessageLevel.INFO),
	
	/**
	 * "Init context parameter '"+ConfigKeys.HAZELCAST_CONFIG+"' is missing", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when in web.xml the hazelcast configuration parameter is missing.<br>Check the web.xml configuration file, adding the right value for Hazelcast configuration.")
	JEMG021E(21, "Init context parameter '"+ConfigKeys.HAZELCAST_CONFIG+"' is missing", MessageLevel.ERROR),
	
	/**
	 * "This is the new installation!", MessageLevel.WARNING
	 */
	@Description(explanation = "It occurs when webappl wasn't able to lock Hazelcast map.<br>Check who is locking map or contact JEM administrator.")
	JEMG022E(22, "Unable to lock \"{0}\" map in 10 seconds", MessageLevel.ERROR),
	
	/**
	 * "This is the new installation!", MessageLevel.WARNING
	 */
	@Description(explanation = "It occurs when webappl wasn't able to have a JEM node to schedule a remote executotion.<br>Contact JEM administrator.")
	JEMG023E(23, "Unable to have a JEM member to perfom task: {0}", MessageLevel.ERROR),
	
	/**
	 * "Hazelcast instance is null", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when Hazelcast instance inside of webapp is null.<br>No action but contact JEM administrator.")
	JEMG024E(24, "Hazelcast instance is null", MessageLevel.ERROR),
	
	/**
	 * "Invalid queue name: {0}", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when the service is called by web client with a wrong parameter, wrong queue name.<br>Contact JEM administrator.")
	JEMG025E(25, "Invalid queue name: {0}", MessageLevel.ERROR),
	
	/**
	 * "Role \"{0}\" already exists", MessageLevel.ERRO
	 */
	@Description(explanation = "It occurs when role is already defined.<br>Check the name of role and change name.")
	JEMG026E(26, "Role \"{0}\" already exists", MessageLevel.ERROR),
	
	/**
	 * "Role \"{0}\" already exists", MessageLevel.ERRO
	 */
	@Description(explanation = "It occurs when role is not defined.<br>Check the name of role and change name.")
	JEMG027E(27, "Role \"{0}\" doen't exist", MessageLevel.ERROR),
	
	/**
	 * "Unable to {0} resource: invalid resource instance \"{1}\"", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when the resource passed to be managed is wrong.<br>Check resource defintion file or command.")
	JEMG028E(28, "Unable to {0} resource: invalid resource instance \"{1}\"", MessageLevel.ERROR),
	
	/**
	 * "Unable to submit job: {0} is null", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs during job submit when the user or password parameter is null.<br>Check command arguments.")
	JEMG029E(29, "Unable to submit job: {0} is null", MessageLevel.ERROR),
	
	/**
	 * "Unable to submit job: {0} is null", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs during job submit when the object passed by http is not a prejob instance.<br>Check command arguments.")
	JEMG030E(30, "Unable to submit job: invalid prejob instance \"{0}\" ", MessageLevel.ERROR),
	
	/**
	 * "Unable to load user {0} attributes", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs during authorization is not able to load attributes for the user.<br>Check security framework configuration.")
	JEMG031E(31, "Unable to load user {0} attributes", MessageLevel.ERROR),
	
	/**
	 * "Unable to submit job: {0} is null", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs during webapp start up, when JEM tried to load the permissions and roles defined for JEM itself.<br>Contact JEM administrator.")
	JEMG032E(32, "Unable to add permissions and roles out-of-the-box", MessageLevel.ERROR),
	
	/**
	 * "Unable to retrieve job because \"{0}\" parameter is missing", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs during calling a servlet to retrieve job by id, the ID or queue name are missing.<br>Contact JEM administrator.")
	JEMG033E(33, "Unable to retrieve job because \"{0}\" parameter is missing", MessageLevel.ERROR),
	
	/**
	 * "Job '\"{0}\"' has been correctly submitted. The job ID is '\"{1}\"'.", MessageLevel.INFO
	 */
	@Description(explanation = "Returns the successful submit of job, reporting the job id.")
	JEMG034I(34, "Job \"{0}\" has been correctly submitted. The job ID is \"{1}\" ", MessageLevel.INFO),
	
	/**
	 * "No job JCL has been submitted. Please check JCL file you choosed", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when the submit service is not able to create a job because same arguments are missing.<br>Contact JEM administrator.")
	JEMG035E(35, "No job JCL has been submitted. Please check JCL file you choosed", MessageLevel.ERROR),
	
	/**
	 * "User id \"{0}\" configured for first installation", MessageLevel.INFO
	 */
	@Description(explanation = "It shows user id configured to be used druing the first installation to define an administrator at least.")
	JEMG036I(36, "User id \"{0}\" configured for first installation", MessageLevel.INFO),
	
	/**
	 * "Unable to execute the service for common resource", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception inside of common resource service.<br>Contact JEM administrator.")
	JEMG037E(37, "Unable to execute the service for common resource", MessageLevel.ERROR),
	
	/**
	 * "Unable to execute the service for jobs", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception inside of jobs service.<br>Contact JEM administrator.")
	JEMG038E(38, "Unable to execute the service for jobs", MessageLevel.ERROR),
	
	/**
	 * "Unable to execute the service for login", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception inside of login service.<br>Contact JEM administrator.")
	JEMG039E(39, "Unable to execute the service for login", MessageLevel.ERROR),
	
	/**
	 * "Unable to execute the service for nodes", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception inside of nodes service.<br>Contact JEM administrator.")
	JEMG040E(40, "Unable to execute the service for nodes", MessageLevel.ERROR),
	
	/**
	 * "Unable to execute the service for roles", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception inside of roles service.<br>Contact JEM administrator.")
	JEMG041E(41, "Unable to execute the service for roles", MessageLevel.ERROR),

	/**
	 * "Unable to execute the service for statistics", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception inside of statistics service.<br>Contact JEM administrator.")
	JEMG042E(42, "Unable to execute the service for statistics", MessageLevel.ERROR),
	
	/**
	 * "Unable to execute the service for info", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception inside of info service.<br>Contact JEM administrator.")
	JEMG043E(43, "Unable to execute the service for info", MessageLevel.ERROR),
	
	/**
	 * "Routing Config \"{0}\" doen't exist", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when routing config is not defined.<br>Contact JEM administrator.")
	JEMG044E(44, "Routing Config \"{0}\" doen't exist", MessageLevel.ERROR),
	
	/**
	 * "Unable to execute the service for GFS", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception inside of gfs service.<br>Contact JEM administrator.")
	JEMG045E(45, "Unable to execute the service for GFS", MessageLevel.ERROR),
	
	/**
	 * "Unable to execute the service for RoutingConfig", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception inside of routing config service.<br>Contact JEM administrator.")
	JEMG046E(46, "Unable to execute the service for RoutingConfig", MessageLevel.ERROR),

	/**
	 * "Inconsistent entity: current: {0}, modified {1}", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when you are trying to modify an entity that someone else has already chanegd and saved.<br>reapply own updates to new instance.")
	JEMG047E(47, "Inconsistent entity: current: {0}, modified {1}", MessageLevel.ERROR),
	
	/**
	 * "Job '\"{0}\"' has been correctly submitted. The job ID is '\"{1}\"'.", MessageLevel.INFO
	 */
	@Description(explanation = "It occurs when it wasn't able to put the job in checking JCL queue. Please check persistent engine (database down) and contact your JEM administrator.")
	JEMG048E(48, "Unable to submit job. Exception: \"{0}\" ", MessageLevel.ERROR),
	
	/**
	 * "Unable to upload file. Exception: \"{0}\" ", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when it wasn't able to upload a file. Please contact your JEM administrator.")
	JEMG049E(49, "Unable to upload file. Exception: \"{0}\" ", MessageLevel.ERROR),
	
	/**
	 * "Unable to create certificate. Parameter \"{0}\" is missing", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when it wasn't able to upload a certificate or userid. Please contact your JEM administrator.")
	JEMG050E(50, "Unable to create certificate. Parameter \"{0}\" is missing", MessageLevel.ERROR),
	
	/**
	 * "Unable to add certificate. Exception: \"{0}\" ", MessageLevel.ERROR);
	 */
	@Description(explanation = "It occurs when it wasn't able to add a certificate and userid. Please contact your JEM administrator.")
	JEMG051E(51, "Unable to add certificate. Exception: \"{0}\" ", MessageLevel.ERROR),
	
	/**
	 * "Alias \"{0}\" has been correctly created", MessageLevel.INFO
	 */
	@Description(explanation = "Returns the successful creation of alias.")
	JEMG052I(52, "Alias \"{0}\" has been correctly created", MessageLevel.INFO),
	
	/**
	 * "Job has been correctly submitted. The job ID is '\"{0}\"'.", MessageLevel.INFO
	 */
	@Description(explanation = "Returns the successful submit of job, reporting the job id.")
	JEMG053I(53, "Job has been correctly submitted. The job ID is \"{0}\" ", MessageLevel.INFO),
	
	/**
	 * "Unable to call purge task for job {0} ", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when you try to pruge jobs by UI but the distributed executor failed. Please contact your JEM administrator.")
	JEMG054E(54, "Unable to call purge task for job {0} ", MessageLevel.ERROR),

	/**
	 * "Unable to execute the service for custom resource definition", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception inside of common resource service.<br>Contact JEM administrator.")
	JEMG055E(55, "Unable to execute the service for custom resource definition", MessageLevel.ERROR),
	
	/**
	 * "Error in multicast service ", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception in the multicast service.")
	JEMG056E(56, "Error in multicast service", MessageLevel.ERROR),

	/**
	 * "Received from host {0} unknown message on JEM multicast group {0}:{1}. Unknown message: {2}"
	 * , MessageLevel.WARNING
	 */
	@Description(explanation = "It occurs when we received an unknown message on JEM multicast group and port.")
	JEMG057W(57, "Received from host {0} unknown message on JEM multicast group {0}:{1}. Unknown message: {2}", MessageLevel.WARNING),

	/**
	 * "Received from host {0} JEM cluster nodes addresses: {0}", MessageLevel.INFO
	 */
	@Description(explanation = "It occurs when we received the list of nodes of JEM cluster from the JEM multicast service.")
	JEMG058I(58, "Received from host {0} JEM cluster nodes addresses: {1}", MessageLevel.INFO),

	/**
	 * "{0} service {1}.", MessageLevel.INFO
	 */
	@Description(explanation = "It occurs when a service change status.")
	JEMG060I(60, "{0} service {1}.", MessageLevel.INFO),

	/**
	 * "Unable to stop {0} service gracefully.", MessageLevel.WARNING
	 */
	@Description(explanation = "It occurs when a service can't be stopped gracefully.")
	JEMG061W(61, "Unable to stop {0} service gracefully.", MessageLevel.WARNING),

	/**
	 * "No JEM Cluster detected try again in {0} seconds.", MessageLevel.WARNING
	 */
	@Description(explanation = "It occurs when the web application cannot connect to the JEM cluster probably because is down.")
	JEMG062W(62, "No JEM Cluster detected try again in {0} seconds.", MessageLevel.WARNING),
	
	/**
	 * "Unable to call executor {0}.", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when the web application is not able to call an executor.<br> Please have a look to generated exception.")
	JEMG063E(63, "Unable to call executor {0}.", MessageLevel.ERROR),
	
	/**
	 * "Unable to indent document {0}.", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when the web application try to indent a document but it receives an error.<br> Please have a look to generated exception.")
	JEMG064E(64, "Unable to indent document {0}.", MessageLevel.ERROR),
	
	/**
	 * "Unable to execute the service for certificates management", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when there is an exception inside of certificate manager.<br>Contact JEM administrator.")
	JEMG065E(65, "Unable to execute the service for certificates management", MessageLevel.ERROR),
	
	/**
	 * "The sleeping thread {0} was unexpectedly interrupted", MessageLevel.INFO
	 */
	@Description(explanation = "It occours when a thread that was sleeping is interrupted for some reasons")
	JEMG066I(66, "The sleeping thread {0} was unexpectedly interrupted", MessageLevel.INFO),
	
	/**
	 * "Unable to call executor. Cause: {0}", MessageLevel.ERROR
	 */
	@Description(explanation = "It occurs when the web application is not able to call an executor.<br> Please have a look to generated exception.")
	JEMG067E(67, "Unable to call executor. Cause: {0}.", MessageLevel.ERROR),
	
	/**
	 * "Keystore file \"{0}\" has been read from system property \"{1}\"", MessageLevel.INFO
	 */
	@Description(explanation = "It occurs when the web application is started and the key store to connect to JEM cluster is mandatory. Informs that the file path is read by system property.")
	JEMG068E(68, "Keystore file \"{0}\" has been read from system property \"{1}\"", MessageLevel.INFO),

	/**
	 * "Keystore file \"{0}\" has been read from Halzelcast config, absolute path", MessageLevel.INFO
	 */
	@Description(explanation = "It occurs when the web application is started and the key store to connect to JEM cluster is mandatory. Informs that the file absolute path is read by Hazelcast configuration file.")
	JEMG069E(69, "Keystore file \"{0}\" has been read from Halzelcast config, absolute path", MessageLevel.INFO),
	
	/**
	 * "Keystore file \"{0}\" has been read from Halzelcast config, realtive path, using web context", MessageLevel.INFO
	 */
	@Description(explanation = "It occurs when the web application is started and the key store to connect to JEM cluster is mandatory. Informs that the file relative path is read by Hazelcast configuration file and uses the ServletContext to read the file.")
	JEMG070E(70, "Keystore file \"{0}\" has been read from Halzelcast config, relative path", MessageLevel.INFO);
	/**
	 * The {@link Message} created in the constructor corresponding to an instance of <code>UserInterfaceMessage</code>. 
	 * @see Message
	 */
	private Message message;
	
	/**
	 * Constructor. It builds a <code>Message</code>. <br>
	 * This method uses the same parameter of the <code>Message</code> constructor
	 * and the specific ID: {@link #MESSAGE_ID}.
	 * 
	 * @param code identifier ID
	 * @param msg string to display. Could contain variables, resolved at runtime
	 * @param level severity of log message
	 * @see Message
	 */
	private UserInterfaceMessage(int code, String messageContent, MessageLevel level){
		this.message = new Message(code, MessageCode.USER_INTERFACE.getCode(), messageContent, level);
	}
	
	/**
	 * It returns the {@link Message} corresponding to an <code>UserInterfaceMessage</code> instance.
	 * @return the {@link Message} corresponding to an <code>UserInterfaceMessage</code> instance.
	 */
	@Override
	public Message toMessage(){
		return this.message;
	}
}
