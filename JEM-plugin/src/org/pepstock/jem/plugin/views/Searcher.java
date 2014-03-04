/*******************************************************************************
* Copyright (c) 2012-2014 pepstock.org.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
* 
* Contributors:
*     Andrea "Stock" Stocchero
******************************************************************************/
package org.pepstock.jem.plugin.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.pepstock.jem.plugin.views.jobs.Refresher;

/**
 * Composite which manages text field and button for searching.
 *    
 * @author Andrea "Stock" Stocchero
 * @version 1.4
 */
public class Searcher extends Composite{
	
	private Refresher refresher = null;
	
	private Text searchText = null;
	
	private Button reload = null;

	/**
	 * Creates the object using the container
	 * @param refresher container of searcher
	 */
    public Searcher(Refresher refresher) {
    	super(refresher.getComposite(), SWT.NONE);
    	setLayout(new GridLayout(3, false));
  	
		GridData data =  new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace = true;
    	setLayoutData(data);

    	// saves refresher
    	this.refresher = refresher;

    	// label
	    Label searchLabel = new Label(this, SWT.NONE);
		searchLabel.setText("Search: ");
		
		// creates final objects
		searchText = new Text(this, SWT.BORDER | SWT.SEARCH);
		reload = new Button(this, SWT.SEARCH);

		searchText.setLayoutData(data);
		// adds listener to catch the return
		searchText.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent arg0) {
				if (arg0.detail == SWT.TRAVERSE_RETURN){
					Searcher.this.refresher.refresh(searchText.getText());
				}
			}
		});
		
		// adds button
		reload.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_REDO));
		reload.setText("Search");
		reload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Searcher.this.refresher.refresh(searchText.getText());
				super.widgetSelected(e);
			}
		});
	}
	
	/**
	 * Sets text to text field. in this case is complete path of file 
	 * @param text
	 */
	public void setText(String text){
		searchText.setText(text);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
	 */
    @Override
    public void setEnabled(boolean enabled) {
		searchText.setEnabled(enabled);
		reload.setEnabled(enabled);
	}
 }