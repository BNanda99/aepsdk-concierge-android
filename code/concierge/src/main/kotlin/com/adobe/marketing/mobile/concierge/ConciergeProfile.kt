/*
  Copyright 2025 Adobe. All rights reserved.
  This file is licensed to you under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software distributed under
  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
  OF ANY KIND, either express or implied. See the License for the specific language
  governing permissions and limitations under the License.
*/

package com.adobe.marketing.mobile.concierge

/** Public APIs for selecting the identity (profile) sent with Brand Concierge requests. */
object ConciergeProfile {

    /**
     * Selects the identityMap sent with subsequent Concierge requests, overriding the identities
     * derived from the EdgeIdentity extension.
     *
     * The identityMap mirrors the XDM structure: a map of namespace (for example "ECID" or "Email")
     * to a list of identities, where each identity contains an `"id"` and may contain `"primary"`.
     * Pass `null` to clear the selection. Switching also starts a fresh conversation session so the
     * new profile does not inherit the previous session.
     *
     * @param identityMap The identityMap to send in requests, or null to clear.
     */
    @JvmStatic
    fun setIdentityMap(identityMap: Map<String, List<Map<String, Any>>>?) {
        ConciergeStateRepository.instance.setSelectedIdentityMap(identityMap)
        ConciergeSessionManager.instance.clearSession()
    }
}
