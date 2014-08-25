/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package webapp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import com.google.inject.util.Providers;

import org.apache.isis.viewer.wicket.ui.app.registry.ComponentFactoryRegistrar;
import org.apache.isis.viewer.wicket.ui.pages.PageClassList;
import org.apache.isis.viewer.wicket.viewer.IsisWicketApplication;

/**
 * As specified in <tt>web.xml</tt>.
 * 
 * <p>
 * See:
 * 
 * <pre>
 * &lt;filter>
 *   &lt;filter-name>wicket&lt;/filter-name>
 *    &lt;filter-class>org.apache.wicket.protocol.http.WicketFilter&lt;/filter-class>
 *    &lt;init-param>
 *      &lt;param-name>applicationClassName&lt;/param-name>
 *      &lt;param-value>webapp.ToDoApplication&lt;/param-value>
 *    &lt;/init-param>
 * &lt;/filter>
 * </pre>
 * 
 */
public class AplicacionRestoTesis extends IsisWicketApplication {

	private static final long serialVersionUID = 1L;

	/**
	 * uncomment for a (slightly hacky) way of allowing logins using query args,
	 * eg:
	 * 
	 * <tt>?user=sven&pass=pass</tt>
	 * 
	 * <p>
	 * for demos only, obvious.
	 */

	@Override
	protected Module newIsisWicketModule() {
		final Module isisDefaults = super.newIsisWicketModule();

		final Module restoTesisOverrides = new AbstractModule() {
			@Override
			protected void configure() {
				bind(ComponentFactoryRegistrar.class).to(
						ComponentFactoryRegistrarForRestoTesis.class);
				bind(PageClassList.class).to(PageClassListForRestoTesis.class);

				bind(String.class)
						.annotatedWith(Names.named("applicationName"))
						.toInstance("ToDo App");
				bind(String.class).annotatedWith(Names.named("applicationCss"))
						.toInstance("css/application.css");
				bind(String.class).annotatedWith(Names.named("applicationJs"))
						.toInstance("scripts/application.js");
				bind(String.class).annotatedWith(Names.named("welcomeMessage"))
						.toInstance(readLines("welcome.html"));
				bind(String.class).annotatedWith(Names.named("aboutMessage"))
						.toInstance("ToDo App");
				bind(InputStream.class).annotatedWith(
						Names.named("metaInfManifest")).toProvider(
						Providers.of(getServletContext().getResourceAsStream(
								"/META-INF/MANIFEST.MF")));
			}
		};

		return Modules.override(isisDefaults).with(restoTesisOverrides);
	}

	private static String readLines(final String resourceName) {
		try {
			List<String> readLines = Resources.readLines(
					Resources.getResource(AplicacionRestoTesis.class, resourceName),
					Charset.defaultCharset());
			final String aboutText = Joiner.on("\n").join(readLines);
			return aboutText;
		} catch (IOException e) {
			return "This is Quick Start";
		}
	}

}
