package animallogic.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;


/*
 * RestApplication is the class where all the REST services / REST Configuration classes have to registered so that REST EASY 
 * identifies them as the REST Web Services along with the configuration classes added.
 * */
public class RestApplication extends Application {

	/**
	 * This set contains objects of providers.
	 */
	private Set<Object> singletons = new HashSet<Object>();
	/**
	 * This set contains all provider class.
	 */
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public RestApplication() {
		singletons.add(new FileTransformationService());
		empty.add(FileTransformationService.class);
		empty.add(JacksonConfig.class);
	}

	/**
	 * Get a set of root resource and provider classes. The default lifecycle
	 * for resource class instances is per-request. The default lifecycle for
	 * providers is singleton.
	 * 
	 * @return a set of root resource and provider classes. Returning null is
	 *         equivalent to returning an empty set.
	 */
	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}

	/**
	 * Get a set of root resource and provider instances. Fields and properties
	 * of returned instances are injected with their declared dependencies by
	 * the runtime prior to use.
	 * <p>
	 * The default implementation returns an empty set.
	 * </p>
	 * 
	 * @return a set of root resource and provider instances. Returning null is
	 *         equivalent to returning an empty set.
	 */
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}

