# InitializingBean
InitializingBean是一个接口，实现这个接口的Bean会对BeanFactory设置完所有的属性作出反应，比如，去执行自定义的初始化，或仅仅检查那些需要被强制设置的属性。
和使用init-method（指定初始化方法）功能类似，执行的顺序在init-method之前。

## 主要作用
* 执行个性化的初始化
* 属性校验：校验需要被设置的属性的值

## 例子
如AbstractCacheResolver实现了InitializingBean接口，用于检查cacheManager为非空，如下：
```java
    @Override
	public void afterPropertiesSet()  {
		Assert.notNull(this.cacheManager, "CacheManager is required");
	}
```

如BeanConfigurerSupport也是类似如上的使用方法。  

如ConcurrentMapCacheFactoryBean使用方式如下，用于个性化初始化：  
```java
    @Override
	public void afterPropertiesSet() {
		this.cache = (this.store != null ? new ConcurrentMapCache(this.name, this.store, this.allowNullValues) :
				new ConcurrentMapCache(this.name, this.allowNullValues));
	}
```

## 源码剖析

```java
// AbstractAutowireCapableBeanFactory.java

protected Object initializeBean(final String beanName, final Object bean, RootBeanDefinition mbd) {
		if (System.getSecurityManager() != null) {
			AccessController.doPrivileged(new PrivilegedAction<Object>() {
				@Override
				public Object run() {
					invokeAwareMethods(beanName, bean);
					return null;
				}
			}, getAccessControlContext());
		}
		else {
			invokeAwareMethods(beanName, bean);
		}

		Object wrappedBean = bean;
		if (mbd == null || !mbd.isSynthetic()) {
			wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
		}

		try {
			invokeInitMethods(beanName, wrappedBean, mbd);
		}
		catch (Throwable ex) {
			throw new BeanCreationException(
					(mbd != null ? mbd.getResourceDescription() : null),
					beanName, "Invocation of init method failed", ex);
		}

		if (mbd == null || !mbd.isSynthetic()) {
			wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
		}
		return wrappedBean;
	}


protected void invokeInitMethods(String beanName, final Object bean, RootBeanDefinition mbd)
			throws Throwable {

		boolean isInitializingBean = (bean instanceof InitializingBean);
		if (isInitializingBean && (mbd == null || !mbd.isExternallyManagedInitMethod("afterPropertiesSet"))) {
			if (logger.isDebugEnabled()) {
				logger.debug("Invoking afterPropertiesSet() on bean with name '" + beanName + "'");
			}
			if (System.getSecurityManager() != null) {
				try {
					AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
						@Override
						public Object run() throws Exception {
							((InitializingBean) bean).afterPropertiesSet();
							return null;
						}
					}, getAccessControlContext());
				}
				catch (PrivilegedActionException pae) {
					throw pae.getException();
				}
			}
			else {
				((InitializingBean) bean).afterPropertiesSet(); // 1
			}
		}

		if (mbd != null) {
			String initMethodName = mbd.getInitMethodName(); 
			if (initMethodName != null && !(isInitializingBean && "afterPropertiesSet".equals(initMethodName)) &&
					!mbd.isExternallyManagedInitMethod(initMethodName)) {
				invokeCustomInitMethod(beanName, bean, mbd); //2 
			}
		}
	}
```
如上，从AbstractApplicationContext的refresh作为入口跟进，最后跟到AbstractAutowireCapableBeanFactory中，从上述代码中的注释1、注释2处代码可以
看出InitializingBean的方法afterPropertiesSet在init-method之前执行。