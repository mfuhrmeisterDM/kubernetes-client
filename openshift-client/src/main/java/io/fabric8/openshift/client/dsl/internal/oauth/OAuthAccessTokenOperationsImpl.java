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
package io.fabric8.openshift.client.dsl.internal.oauth;

import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.internal.HasMetadataOperationsImpl;
import io.fabric8.kubernetes.client.dsl.internal.OperationContext;
import io.fabric8.openshift.api.model.OAuthAccessToken;
import io.fabric8.openshift.api.model.OAuthAccessTokenList;
import io.fabric8.openshift.client.OpenshiftClientContext;
import io.fabric8.openshift.client.dsl.internal.OpenShiftOperation;

import static io.fabric8.openshift.client.OpenShiftAPIGroups.OAUTH;

public class OAuthAccessTokenOperationsImpl extends OpenShiftOperation<OAuthAccessToken, OAuthAccessTokenList,
  Resource<OAuthAccessToken>> {

  public OAuthAccessTokenOperationsImpl(OpenshiftClientContext clientContext) {
    this(HasMetadataOperationsImpl.defaultContext(clientContext));
  }

  public OAuthAccessTokenOperationsImpl(OperationContext context) {
    super(context.withApiGroupName(OAUTH)
      .withPlural("oauthaccesstokens"), OAuthAccessToken.class, OAuthAccessTokenList.class);
  }

  @Override
  public OAuthAccessTokenOperationsImpl newInstance(OperationContext context) {
    return new OAuthAccessTokenOperationsImpl(context);
  }

  @Override
  public boolean isResourceNamespaced() {
    return false;
  }
}
