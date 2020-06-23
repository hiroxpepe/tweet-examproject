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

package org.examproject.tweet.repository

import java.lang.Long
import java.util.Date
import java.util.List

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

import org.examproject.tweet.entity.Tweet

/**
 * @author hiroxpepe
 */
trait TweetRepository extends JpaRepository[Tweet, Long] {
    
    def findById(id: Long): Tweet
    
    def findByName(name: String): List[Tweet]
    
    def findByDateBetween(begin: Date, end: Date): List[Tweet]
    
    def findByNameAndDateBetween(name: String, begin: Date, end: Date): List[Tweet]
    
    def findByNameOrderByDateDesc(name: String): List[Tweet]
    
    @Query(value="SELECT t FROM Tweet t WHERE (t.name = ?1) AND (LOWER(t.text) LIKE LOWER(?2))")
    def findByNameAndTextLike(name: String, text: String): List[Tweet]
}