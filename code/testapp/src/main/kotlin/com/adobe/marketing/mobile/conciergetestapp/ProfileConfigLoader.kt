/*
 * Copyright 2025 Adobe. All rights reserved.
 * This file is licensed to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.adobe.marketing.mobile.conciergetestapp

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

/**
 * A selectable profile loaded from `assets/profiles.json`.
 *
 * @property identityMap The identityMap to send with Concierge requests, or null to use the
 *                       EdgeIdentity identityMap (the "default" profile).
 */
data class ProfileOption(
    val key: String,
    val label: String,
    val identityMap: Map<String, List<Map<String, Any>>>?
)

/**
 * Loads the list of [ProfileOption]s from `assets/profiles.json` so the profile values live in
 * config rather than being hardcoded in the UI.
 */
object ProfileConfigLoader {
    private const val TAG = "ProfileConfigLoader"
    private const val FILE_NAME = "profiles.json"

    fun load(context: Context): List<ProfileOption> = try {
        val json = context.assets.open(FILE_NAME).bufferedReader().use { it.readText() }
        val array = JSONArray(json)
        (0 until array.length()).map { i ->
            val obj = array.getJSONObject(i)
            ProfileOption(
                key = obj.getString("key"),
                label = obj.getString("label"),
                identityMap = obj.optJSONObject("identityMap")?.let { parseIdentityMap(it) }
            )
        }
    } catch (e: Exception) {
        Log.w(TAG, "Failed to load $FILE_NAME: ${e.message}")
        emptyList()
    }

    private fun parseIdentityMap(obj: JSONObject): Map<String, List<Map<String, Any>>> =
        obj.keys().asSequence().associateWith { namespace ->
            val arr = obj.getJSONArray(namespace)
            (0 until arr.length()).map { i ->
                val item = arr.getJSONObject(i)
                item.keys().asSequence().associateWith { key -> item.get(key) }
            }
        }
}
