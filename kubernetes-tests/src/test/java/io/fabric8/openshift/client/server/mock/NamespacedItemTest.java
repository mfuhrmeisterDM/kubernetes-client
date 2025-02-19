/**
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fabric8.openshift.client.server.mock;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ConfigMapList;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.NamespacedKubernetesClient;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.openshift.client.OpenShiftClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@EnableOpenShiftMockClient(crud = true)
class NamespacedItemTest {
  private OpenShiftClient client;

  ConfigMap configmap =
      new ConfigMapBuilder().withNewMetadata().withName("name").withNamespace("item").endMetadata().build();

  @Test
  void testDefaultNamespace() {
    ConfigMap created = this.client
        .configMaps()
        .withItem(configmap)
        .create();

    // should create in the item namespace rather than the default - no exception
    assertEquals("item", created.getMetadata().getNamespace());
  }

  @Test
  void testClientExplicitNamespace() {
    ConfigMap created = this.client.adapt(NamespacedKubernetesClient.class)
        .inNamespace("explicit")
        .configMaps()
        .withItem(configmap)
        .create();

    // should create in the explicit, rather than the item - no exception
    assertEquals("explicit", created.getMetadata().getNamespace());
  }

  @Test
  void testOperationExplicitNamespace() {
    ConfigMap created = this.client
        .configMaps()
        .inNamespace("explicit")
        .withItem(configmap)
        .create();

    // should create in the explicit, rather than the item - no exception
    assertEquals("explicit", created.getMetadata().getNamespace());

    ConfigMap modified = this.client
        .configMaps()
        .inNamespace("explicit")
        .withItem(configmap)
        .get();

    // should create in the explicit, rather than the item - no exception
    assertEquals("explicit", modified.getMetadata().getNamespace());
  }

  @Test
  void testResource() {
    ConfigMap created = this.client.resource(configmap).create();

    assertEquals("item", created.getMetadata().getNamespace());
  }

  @Test
  void testExplicitResource() {
    ConfigMap created = this.client.resource(configmap).inNamespace("explicit").create();

    assertEquals("explicit", created.getMetadata().getNamespace());
  }

  @Test
  void testClientExplicitResourceNamespace() {
    ConfigMap created = this.client.adapt(NamespacedKubernetesClient.class)
        .inNamespace("explicit")
        .resource(configmap)
        .create();

    assertEquals("explicit", created.getMetadata().getNamespace());
  }

  @Test
  void testClientNullNamespace() {
    NamespacedKubernetesClient nsclient = this.client.adapt(NamespacedKubernetesClient.class);
    assertThrows(KubernetesClientException.class, () -> nsclient.inNamespace(null));
  }

  @Test
  void testOperationNullNamespace() {
    MixedOperation<ConfigMap, ConfigMapList, Resource<ConfigMap>> configMaps = this.client.configMaps();
    assertThrows(KubernetesClientException.class, () -> configMaps.inNamespace(null));
  }

}
