/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ghidra.app.plugin.core.compositeeditor;

import ghidra.app.context.ProgramActionContext;
import ghidra.program.model.data.*;
import ghidra.program.model.listing.Program;

/**
 * <code>ComponentProgramActionContext</code> provides an action context when editing a 
 * composite with a single selected component, and the composite is associated with a program. 
 */
public class ComponentProgramActionContext extends ProgramActionContext
		implements ComponentContext {

	private DataTypeComponent component;
	private Composite composite;

	public ComponentProgramActionContext(CompositeEditorProvider<?, ?> compositeEditorProvider,
			Program program, DataTypeComponent component) {
		super(compositeEditorProvider, program);
		this.component = component;
		DataType parent = component.getParent();
		if (!(parent instanceof Composite)) {
			throw new IllegalArgumentException("Only Composite components allowed");
		}
		this.composite = (Composite) parent;
		if (parent.getDataTypeManager() == null) {
			throw new IllegalArgumentException("Component's parent must have a DataTypeManager");
		}
	}

	@Override
	public DataTypeManager getDataTypeManager() {
		// Pass target datatype manager - not intended to be modified!
		return program.getDataTypeManager();
	}

	@Override
	public Composite getCompositeDataType() {
		return composite;
	}

	@Override
	public DataTypeComponent getDataTypeComponent() {
		return component;
	}
}
