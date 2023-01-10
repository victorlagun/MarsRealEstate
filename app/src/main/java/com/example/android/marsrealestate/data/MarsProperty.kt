/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.data

import com.google.gson.annotations.SerializedName

data class MarsProperty(
    val price: Long,
    val id: String,
    val type: String,
    @SerializedName("img_src")
    val imgSrc: String,
) {
    fun toEntity() = com.example.android.marsrealestate.db.MarsProperty(
        id = this.id,
        price = this.price,
        type = this.type,
        imgSrc = this.imgSrc
    )
}
