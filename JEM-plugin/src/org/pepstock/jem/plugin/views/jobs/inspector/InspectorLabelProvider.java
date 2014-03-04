/*******************************************************************************
 * Copyright (c) 2012-2013 pepstock.org.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Enrico - initial API and implementation
 ******************************************************************************/
package org.pepstock.jem.plugin.views.jobs.inspector;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.pepstock.jem.plugin.views.jobs.inspector.model.Category;
import org.pepstock.jem.plugin.views.jobs.inspector.model.ProducedOutput;

/**
 * This is teh label provider for the tree of job insopector. Returns default
 * image for folder when we have a category, default image for file for ouput
 * file.
 * 
 * @author Andrea "Stock" Stocchero
 * @version 1.4
 */
public class InspectorLabelProvider extends LabelProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof Category) {
			Category category = (Category) element;
			if (category.getImage() != null) {
				return category.getImage();
			}
		} else if (element instanceof ProducedOutput) {
			ProducedOutput output = (ProducedOutput) element;
			if (output.getImage() != null) {
				return output.getImage();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof Category) {
			Category category = (Category) element;
			return category.getName();
		}
		return ((ProducedOutput) element).getName();
	}
}
