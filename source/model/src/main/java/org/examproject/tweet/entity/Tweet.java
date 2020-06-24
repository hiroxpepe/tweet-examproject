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

package org.examproject.tweet.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author hiroxpepe
 */
@Data
@Entity
@Table(name="tweets")
@Component
public class Tweet implements Serializable {

    @Id
    @Column(unique=true)
    Long id;

    @Column(name="date")
    @Temporal(TemporalType.TIMESTAMP)
    Date date = new Date();

    @Column(name="name")
    String name;

    @Column(name="text")
    String text;

    @OneToMany(mappedBy="status")
    Set<Vocab> vocabSet = new HashSet<Vocab>();

}