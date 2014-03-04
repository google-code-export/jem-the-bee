package org.pepstock.jem.gwt.client.panels.resources.inspector.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pepstock.jem.gwt.client.Sizes;
import org.pepstock.jem.gwt.client.commons.CSVUtil;
import org.pepstock.jem.node.resources.ResourceProperty;
import org.pepstock.jem.node.resources.custom.fields.MultiSelectableListFieldDescriptor;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;

/**
 * Build a list-based property field, with none, one or more values that can be selected.
 * Usually rendered as a bounce of {@link CheckBox}
 * @author Marco "Fuzzo" Cuccato
 *
 */
public final class CheckBoxesFieldPanel extends AbstractFieldPanel<MultiSelectableListFieldDescriptor, Grid, String[]> {

	protected CheckBox[] checkBoxes = null;
	
	/**
	 * Builds the panel
	 * @param descriptor the descriptor who knows hoe to render the panel 
	 * @param panel the parent panel
	 */
	public CheckBoxesFieldPanel(MultiSelectableListFieldDescriptor descriptor, CustomResourcePropertiesPanel<?> panel) {
		super(descriptor, panel);
		build();
	}

	@Override
	protected final void build() {
		inputObject = new Grid((int)Math.ceil(getDescriptor().getValues().size() / 2f), 2);
		inputObject.setWidth(Sizes.HUNDRED_PERCENT);
		checkBoxes = new CheckBox[getDescriptor().getValues().size()];
		
		// builds the boxes and add them to panel
		Handler handler = new Handler();
		List<String> valueList = new ArrayList<String>(getDescriptor().getValues());
		Collections.sort(valueList);
		int i = 0;
		int row = 0;
		for (String v : valueList) {
			CheckBox cb = new CheckBox(v);
			// events
			cb.addValueChangeHandler(handler);
			
			checkBoxes[i] = cb;
			inputObject.setWidget(row, i % 2 == 0 ? 0 : 1, cb);
			if (++i % 2 == 0) {
				row++;
			}
		}
		
		ResourceProperty existingProperty = getPanel().getResource().getProperties().get(getDescriptor().getKey());
		if (existingProperty != null) {
			setSelectedValue(CSVUtil.splitAndTrim(existingProperty.getValue()));
			// save not needed because it's aloaded property
		} else if (getDescriptor().hasDefaultValues()) {
			Set<String> defaultValue = getDescriptor().getDefaultValues();
			String[] defaultValueArray = defaultValue.toArray(new String[0]);
			setSelectedValue(defaultValueArray);
			saveProperty(defaultValueArray);
		}
	}

	@Override
	public boolean checkMandatory() {
		if (getDescriptor().isMandatory()) {
			for (CheckBox cb : checkBoxes) {
				if (cb.getValue()) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public void loadProperties() {
		Map<String, ResourceProperty> props = getPanel().getResource().getProperties();
		ResourceProperty p = props.get(getDescriptor().getKey());
		if (p != null && p.getValue() != null) {
			String[] toBeSelectedValues = CSVUtil.split(p.getValue());
			setSelectedValue(toBeSelectedValues);
		}
	}

	@Override
	public String[] getSelectedValue() {
		Set<String> selected = new HashSet<String>();
		for (CheckBox cb : checkBoxes) {
			if (cb.getValue()) {
				selected.add(cb.getText());
			}
		}
		return selected.toArray(new String[0]);
	}
	
	@Override
	public void setSelectedValue(String[] value) {
		List<String> toBeSelectedTexts = Arrays.asList(value);
		for (CheckBox cb : checkBoxes) {
			if (toBeSelectedTexts.contains(cb.getText())) {
				cb.setValue(true);
			} else {
				cb.setValue(false);
			}
		}
	}

	@Override
	public void saveProperty(String[] value) {
		String key = getDescriptor().getKey();
		getPanel().getResource().setProperty(key, CSVUtil.getCSVPhrase(value));
		setCommonPropertyAttributes();
	}

	@Override
	public boolean validate() {
		// check boxes cannot have wrong values
		return true;
	}

	class Handler implements ValueChangeHandler<Boolean> {

		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			String[] value = getSelectedValue();
			saveProperty(value);
		}
	}
	
}
