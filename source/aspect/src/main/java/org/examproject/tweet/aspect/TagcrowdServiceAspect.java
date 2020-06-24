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

package org.examproject.tweet.aspect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.examproject.tweet.dto.TagcrowdDto;
import org.examproject.tweet.util.IsContainOfEmoticonWords;

/**
 * @author h.adachi
 */
@Aspect
public class TagcrowdServiceAspect {

    private static final Log LOG = LogFactory.getLog(
        TagcrowdServiceAspect.class
    );

    ///////////////////////////////////////////////////////////////////////////
    // public methods

    @Around("execution(* org.examproject.tweet.service.TagcrowdService.getTagcrowdListByName(..))")
    @SuppressWarnings("unchecked")
    public Object getTagcrowdListByNameAround(ProceedingJoinPoint pjp) throws Throwable {
        LOG.debug("called.");

        // do the process.
        Object result = pjp.proceed();

        // get filtered result.
        List<TagcrowdDto> filteredResult = getFilteredResult(result);

        // sort by count on desc.
        Comparator comparator = new ReverseComparator(
            new CountComparator()
        );
        Collections.sort(
            filteredResult,
            comparator
        );

        // must return the object.
        return filteredResult;
    }

    ///////////////////////////////////////////////////////////////////////////
    // private methods

    private List<TagcrowdDto> getFilteredResult(Object result) {

        // a checker of sentence.
        Predicate emPredicate = new IsContainOfEmoticonWords();

        // create a new tagcrowd list for return.
        List<TagcrowdDto> filteredResult = new ArrayList<TagcrowdDto>();

        // search all of tagcrowds.
        for (TagcrowdDto tagcrowdDto : (List<TagcrowdDto>) result) {

            // get a tagcrowd.
            String text = tagcrowdDto.getText();

            // if it contain emoticon word.
            if (emPredicate.evaluate(text)) {
                continue;
            }

            // other, set the tagcrowd to new list.
            filteredResult.add(tagcrowdDto);
        }

        return filteredResult;
    }

    ///////////////////////////////////////////////////////////////////////////
    // private classes.

    ///////////////////////////////////////////////////////////////////////////
    /**
     * sort list by count.
     */
    class CountComparator implements Comparator<TagcrowdDto> {

        @Override
        public int compare(TagcrowdDto o1, TagcrowdDto o2 ) {
            if (o1.getCount() == null) {
                return 0;
            }
            if (o2.getCount() == null) {
                return 0;
            }

            int i = o1.getCount().compareTo(o2.getCount());
            if (i < 0) {
                return -1;
            } else if (i > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}