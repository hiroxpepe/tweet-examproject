/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.examproject.tweet.response;

import java.util.List;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.examproject.tweet.model.TagcrowdModel;

/**
 * @author h.adachi
 */
@RequiredArgsConstructor
@Data
public class TagcrowdResponse {

    @NonNull
    private final  List<TagcrowdModel> tagcrowdModelList;

    Boolean isError = false;

    String message;

    public TagcrowdResponse(Boolean isError, String message) {
        this(null);
        this.isError = isError;
        this.message = message;
    }

}
