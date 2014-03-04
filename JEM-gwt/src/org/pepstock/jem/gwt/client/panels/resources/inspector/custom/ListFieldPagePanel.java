package org.pepstock.jem.gwt.client.panels.resources.inspector.custom;

import org.pepstock.jem.gwt.client.Sizes;
import org.pepstock.jem.node.resources.Resource;
import org.pepstock.jem.node.resources.custom.ResourceDescriptor;
import org.pepstock.jem.node.resources.custom.SectionDescriptor;

import com.google.gwt.user.client.ui.Label;

/**
 * Builds a special page panel, that contains only a list field 
 * @author Marco "Fuzzo" Cuccato
 *
 */
public class ListFieldPagePanel extends PagePropertiesPanel {

	/**
	 * Builds the panel
	 * @param resource the underlying resource
	 * @param descriptor the {@link ResourceDescriptor}
	 */
	public ListFieldPagePanel(Resource resource, SectionDescriptor descriptor) {
		this(resource, descriptor, null);
	}

	/**
	 * Builds the panel
	 * @param resource the underlying resource
	 * @param descriptor the {@link ResourceDescriptor}
	 * @param resourceType the resource type, needed for single-page custom resource
	 */
	public ListFieldPagePanel(Resource resource, SectionDescriptor descriptor, String resourceType) {
		super(resource, descriptor, resourceType);
	}

	/**
	 * @throws wrap a {@link #addListFieldPanel(ListFieldPanel)} call
	 */
	@Override
	public void addFieldPanel(AbstractFieldPanel<?, ?, ?> fieldPanel) {
		if (fieldPanel instanceof ListFieldPanel && fields.isEmpty()) {
			ListFieldPanel listFieldPanel = (ListFieldPanel)fieldPanel;
			fields.add(listFieldPanel);
			getTable().setSize(Sizes.HUNDRED_PERCENT, Sizes.HUNDRED_PERCENT);
			getTable().setWidget(0, 0, new Label(listFieldPanel.getDescription()));
			getTable().setWidget(1, 0, fieldPanel.getInputObject());
			stylized = true;
		} else {
			throw new IllegalArgumentException("You can only add one ListFieldPanel!");
		}
	}

	@Override
	public void onResize(int availableWidth, int availableHeight) {
		super.onResize(availableWidth, availableHeight);
		if (!fields.isEmpty()) {
			int newAvailableHeight = availableHeight - Sizes.FOOTER - 6 * getTable().getCellSpacing();
			((ListFieldPanel)fields.get(0)).getInputObject().onResize(availableWidth, newAvailableHeight);
		}
	}
}
