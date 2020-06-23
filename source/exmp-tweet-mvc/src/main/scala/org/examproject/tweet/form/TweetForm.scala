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

package org.examproject.tweet.form

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import reflect.BeanProperty

/**
 * @author hiroxpepe
 */
@Component
@Scope(value="prototype")
class TweetForm {

    @BeanProperty
    var content: String = _
    
    @BeanProperty
    var locale: String = _

    @BeanProperty
    var oauthToken: String = _

    @BeanProperty
    var oauthTokenSecret: String = _

    @BeanProperty
    var userId: String = _

    @BeanProperty
    var screenName: String = _
    
    @BeanProperty
    var responseListMode: String = _
    
    @BeanProperty
    var userListName: String = _

    def clear() {
        content = "";
        locale = "";
        oauthToken = "";
        oauthTokenSecret = "";
        userId = "";
        screenName = "";
        responseListMode = "";
        userListName = "";
    }
    
}