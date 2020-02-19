/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Generates unique id for quizzes.
 */
@Service
public class IdService {
    private static final Random RANDOM = new Random();
    private static final char[] CHARS =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();
    private static final int SIZE = 10;

    public final String generate() {
        return NanoIdUtils.randomNanoId(RANDOM, CHARS, SIZE);
    }
}
