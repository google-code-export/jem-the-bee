/**
    JEM, the BEE - Job Entry Manager, the Batch Execution Environment
    Copyright (C) 2012-2015   Andrea "Stock " Stocchero
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
package org.pepstock.jem.springbatch;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.pepstock.jem.Jcl;
import org.pepstock.jem.Job;
import org.pepstock.jem.factories.AbstractFactory;
import org.pepstock.jem.factories.JclFactoryException;
import org.pepstock.jem.node.Main;
import org.pepstock.jem.node.configuration.ConfigKeys;
import org.pepstock.jem.node.tasks.JobTask;
import org.pepstock.jem.springbatch.xml.JemBeanDefinitionParser;
import org.pepstock.jem.util.CharSet;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

/**
 * This implements JemFactory interface to create, check and validate JCL for
 * SpringBatch. It creates JCL object.
 * 
 * @see org.pepstock.jem.factories.JemFactory
 * @author Andrea "Stock " Stocchero
 * 
 */
public class SpringBatchFactory extends AbstractFactory {

	private static final long serialVersionUID = 1L;

	/**
	 * JCL type for Springbatch
	 */
	public static final String SPRINGBATCH_TYPE = "sb";
	
	private static final String SPRINGBATCH_TYPE_DESCRIPTION = "Spring Batch";
	
	/**
	 * Empty constructor. Do nothing
	 */
	public SpringBatchFactory() {
	}

	/**
	 * Called to create a JCL object, by the string representing source code of
	 * SpringBatch.
	 * 
	 * @see org.pepstock.jem.factories.JclFactory#createJcl(java.lang.String)
	 * @return JCL object
	 * @throws JclFactoryException if syntax is not correct, a exception occurs
	 */
	@Override
	public Jcl createJcl(String content) throws JclFactoryException {
		// creates JCL object setting the source code
		Jcl jcl = new Jcl();
		jcl.setType(SPRINGBATCH_TYPE);
		jcl.setContent(content);
		
		// check validation of content
		try {
			validate(jcl);
		} catch (Exception e) {
			throw new JclFactoryException(jcl, e);
		}
		return jcl;
	}

	/**
	 * It validates XML syntax of SpringBatch source code. <br>
	 * It doesn't use BeanFactory of Spring because too slow and because there
	 * aren't all classes in JEM classpath (the business logic ones).<br>
	 * It extracts Job Name from <code>&lt;job&gt;</code> element. Furthermore
	 * extract all information of JemBean.
	 * 
	 * @see JemBean
	 * @see SpringBatchKeys#BEAN_ID
	 * 
	 * @param jcl JCL instance with source code
	 * @throws SpringBatchException if any exception occurs
	 * @throws SAXException if any exception occurs
	 * @throws IOException if any exception occurs
	 */
	private void validate(Jcl jcl) throws SpringBatchException, SAXException, IOException{
		XMLParser parser;
		JemBean bean = null;

		// loads beans
		Resource res = new ByteArrayResource(jcl.getContent().getBytes(CharSet.DEFAULT));
		
		DefaultListableBeanFactory factory = new  DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(res);
		
		// checks if bean is present. if not, exception
		if (!factory.containsBean(SpringBatchKeys.BEAN_ID)){
			throw new SpringBatchException(SpringBatchMessage.JEMS035E, SpringBatchKeys.BEAN_ID);
		}
		@SuppressWarnings("rawtypes")
		Class jemBeanClass = factory.getType(SpringBatchKeys.BEAN_ID);

		// checks if bean loaded is equals to JemBean. if not, exception
		if (!jemBeanClass.equals(JemBean.class)){
			throw new SpringBatchException(SpringBatchMessage.JEMS036E, SpringBatchKeys.BEAN_ID, JemBean.class.getName(), jemBeanClass.getName());
		}
		bean = (JemBean) factory.getBean(SpringBatchKeys.BEAN_ID);

		// use a SAX parser to extract all info for Jem Bean
		parser = new XMLParser(new StringReader(jcl.getContent()));
		String jobName = parser.parse();
		parser.close();
		bean.setJobName(jobName);

		// job name must be set, otherwise an exception occurs
		if (bean.getJobName() == null){
			throw new SpringBatchException(SpringBatchMessage.JEMS020E);
		}

		// if environment is not set, use the same environment of node which
		// perform validation
		if (bean.getEnvironment() == null) {
			bean.setEnvironment(Main.EXECUTION_ENVIRONMENT.getEnvironment());
		}

		// if domain is not set, use the default value
		if (bean.getDomain() == null) {
			bean.setDomain(Jcl.DEFAULT_DOMAIN);
		}
		
		// Extracts from SpringBatch email addresses notification property
		// Sets the value in the Jcl.
		String emailAddresses = bean.getEmailNotificationAddresses();
		if(null != emailAddresses) {
			jcl.setEmailNotificationAddresses(emailAddresses);
		}

		// Extracts from SpringBatch user property
		// Sets the value in the Jcl.
		String user = bean.getUser();
		if(null != user) {
			jcl.setUser(user);
		}

		// if affinity is not set, use the default value
		if (bean.getAffinity() == null) {
			bean.setAffinity(Jcl.DEFAULT_AFFINITY);
		}

		// if classpath is not set, changes if some variables are in
		if (bean.getClassPath() != null) {
			String classPath = bean.getClassPath();
			bean.setClassPath(super.resolvePathNames(classPath, ConfigKeys.JEM_CLASSPATH_PATH_NAME));
		}

		// if priorclasspath is not set, changes if some variables are in
		if (bean.getPriorClassPath() != null) {
			String classPath = bean.getPriorClassPath();
			bean.setPriorClassPath(super.resolvePathNames(classPath, ConfigKeys.JEM_CLASSPATH_PATH_NAME));
		}

		// load all information inside JCL object
		jcl.setJobName(bean.getJobName());
		jcl.setEnvironment(bean.getEnvironment());
		jcl.setDomain(bean.getDomain());
		jcl.setAffinity(bean.getAffinity());
		jcl.setHold(bean.isHold());
		jcl.setPriority(bean.getPriority());
		jcl.setMemory(bean.getMemory());
		
		jcl.setClassPath(bean.getClassPath());
		jcl.setPriorClassPath(bean.getPriorClassPath());
		
		
    	// loads the JBPM process ID in a map to reuse when a JBPM task will be scheduled
    	Map<String, Object> jclMap = new HashMap<String, Object>();
		// if options are set, add to JCL
		if (bean.getOptions() != null) {
			jclMap.put(JemBeanDefinitionParser.OPTIONS_ATTRIBUTE, bean.getOptions());
		}
		// if parameters are set, add to JCL
		if (bean.getParameters() != null) {
			jclMap.put(JemBeanDefinitionParser.PARAMETERS_ATTRIBUTE, bean.getParameters());
		}
		jcl.setProperties(jclMap);
	}

	/**
	 * It returns new instance of SpringBatchTask.
	 * 
	 * @see SpringBatchTask
	 * @see org.pepstock.jem.node.tasks.JobTask
	 * @see org.pepstock.jem.factories.JobTaskFactory#createJobTask(org.pepstock.jem.Job)
	 * @param job job instance to execute
	 * @return Job task instance so JEM node can execute it
	 */
	@Override
	public JobTask createJobTask(Job job) {
		return new SpringBatchTask(job, this);
	}

	/**
	 * Returns the type of this factory. This is unique key (value "sb" means
	 * SpringBatch) to search factory loaded during startup.
	 * 
	 * @see org.pepstock.jem.node.Main#FACTORIES_LIST
	 * @see org.pepstock.jem.node.StartUpSystem#run()
	 * @see org.pepstock.jem.factories.JemFactory#getType()
	 */
	@Override
	public String getType() {
		return SPRINGBATCH_TYPE;
	}

	/* (non-Javadoc)
	 * @see org.pepstock.jem.factories.JemFactory#getTypeDescription()
	 */
	@Override
	public String getTypeDescription() {
		return SPRINGBATCH_TYPE_DESCRIPTION;
	}
}