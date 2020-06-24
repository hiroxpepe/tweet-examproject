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

package org.examproject.tweet.util;

import java.util.ArrayList;

import org.apache.commons.collections.Predicate;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

/**
 * @author h.adachi
 */
public class IsEnglishSentencePredicate implements Predicate {
    
    ///////////////////////////////////////////////////////////////////////////
    // fields
       
    private Detector detector = null;
    
    ///////////////////////////////////////////////////////////////////////////
    // contractor
    
    public IsEnglishSentencePredicate() {
        try {
            if (DetectorFactory.getLangList().isEmpty()) {
                String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
                DetectorFactory.loadProfile(path + "profiles");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    @Override
    public boolean evaluate(Object object) {

        String text = (String) object;
        try {
            detector = DetectorFactory.create();
            detector.append(text);

            ArrayList<Language> languages = detector.getProbabilities();
            for (Language language : languages) {
                if (language.lang.equals("en")) {
                    return true;
                }
            }
            return false;
            
        } catch (LangDetectException lde) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}