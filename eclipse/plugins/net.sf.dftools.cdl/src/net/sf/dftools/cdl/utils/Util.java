
package net.sf.dftools.cdl.utils;

import net.sf.dftools.cdl.cdl.Module;

import org.eclipse.emf.ecore.EObject;


public class Util {

	/**
	 * Returns the qualified name of the given entity as
	 * <code>package + "." + name</code>. If <code>package</code> is
	 * <code>null</code>, only the name is returned.
	 * 
	 * @param entity
	 *            an entity
	 * @return the qualified name of the given entity
	 */
	public static String getQualifiedName(Module module) {
		String packageName = module.getPackage();
		String simpleName = module.getName();

		String name = simpleName;
		if (packageName != null) {
			name = packageName + "." + name;
		}

		return name;
	}

	/**
	 * Returns the top-level container in which <code>context</code> occurs.
	 * 
	 * @param context
	 *            an object
	 * @return the top-level container in which <code>context</code> occurs
	 */
	public static EObject getTopLevelContainer(EObject context) {
		EObject cter = context.eContainer();
		if (cter == null) {
			return context;
		} else {
			return getTopLevelContainer(cter);
		}
	}

}
