package net.sf.dftools.cdl.naming;

import java.util.Collections;

import net.sf.dftools.cdl.cdl.Module;
import net.sf.dftools.cdl.cdl.Library;
import net.sf.dftools.cdl.utils.Util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.SimpleAttributeResolver;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * This class defines a qualified name provider for RVC-CAL.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CdlQualifiedNameProvider extends
		IQualifiedNameProvider.AbstractImpl {

	@Inject
	private IResourceScopeCache cache = IResourceScopeCache.NullImpl.INSTANCE;

	private PolymorphicDispatcher<String> qualifiedName = new PolymorphicDispatcher<String>(
			"qualifiedName", 1, 1, Collections.singletonList(this),
			PolymorphicDispatcher.NullErrorHandler.<String> get()) {
		@Override
		protected String handleNoSuchMethod(Object... params) {
			return null;
		}
	};

	private SimpleAttributeResolver<EObject, String> resolver = SimpleAttributeResolver
			.newResolver(String.class, "name");

	public String getDelimiter() {
		return ".";
	}

	public String getQualifiedName(final EObject obj) {
		return cache.get(Tuples.pair(obj, "fqn"), obj.eResource(),
				new Provider<String>() {

					public String get() {
						EObject temp = obj;
						String name = qualifiedName.invoke(temp);
						if (name != null) {
							return name;
						}

						Module module = null;
						if (temp instanceof Module) {
							// return qualified name of module
							module = (Module) temp;
						} else if (temp.eContainer() instanceof Module) {
							// return qualified name of module
							module = (Module) temp.eContainer();
						}

						if (module == null) {
							// object inside an entity
							EObject cter = temp.eContainer();
							if (cter instanceof Library) {
								String parentsName = getQualifiedName(cter);
								String value = resolver.getValue(temp);
								if (value != null) {
									return parentsName + getDelimiter() + value;
								}
							}
						} else {
							return Util.getQualifiedName(module);
						}

						return null;
					}

				});

	}

	protected SimpleAttributeResolver<EObject, String> getResolver() {
		return resolver;
	}

	public String getWildcard() {
		return "*";
	}

	/*public String qualifiedName(AstTag tag) {
		return OrccUtil.toString(tag.getIdentifiers(), ".");
	}*/

}
