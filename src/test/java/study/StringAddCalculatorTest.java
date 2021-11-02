package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringAddCalculatorTest {
    private static Stream<Arguments> insNullOrEmptyTestArguments() {
        return Stream.of(
                Arguments.of(null, true)
                , Arguments.of("", true)
                , Arguments.of("1", false)
                , Arguments.of("2", false)
        );
    }

    private static Stream<Arguments> splitStringTestArguments() {
        return Stream.of(
                Arguments.of(null, Collections.emptyList())
                , Arguments.of("", Collections.emptyList())
                , Arguments.of("1", Collections.singletonList("1"))
                , Arguments.of("1,2:3", Arrays.asList("1", "2", "3"))
                , Arguments.of("//;\n1;2;3", Arrays.asList("1", "2", "3"))
        );
    }

    private static Stream<Arguments> validateStringListTestArguments() {
        return Stream.of(
                Arguments.of(Arrays.asList("1", "2", "3"), true)
                , Arguments.of(Arrays.asList("-1", "1", "3"), false)
                , Arguments.of(Arrays.asList("a", "b", "c"), false)
        );
    }

    @DisplayName("Null과 Empty String 확인")
    @ParameterizedTest(name = "{displayName} ({index}) -> param = [{0}, {1}]")
    @MethodSource("insNullOrEmptyTestArguments")
    void isNullOrEmptyTest(String testValue, boolean expectedResult) {
        // when
        boolean testResult = StringAddCalculator.isNullOrEmpty(testValue);

        // then
        assertThat(testResult)
                .isEqualTo(expectedResult);
    }

    @DisplayName("Split String 테스트")
    @ParameterizedTest(name = "{displayName} ({index}) -> param = [{0}, {1}]")
    @MethodSource("splitStringTestArguments")
    void splitStringTest(String testValue, List<String> expectedResult) {
        // when
        List<String> testResult = StringAddCalculator.splitString(testValue);

        // then
        assertThat(testResult)
                .isEqualTo(expectedResult);
    }

    @DisplayName("String List 검증")
    @ParameterizedTest(name = "{displayName} ({index}) -> param = [{arguments}]")
    @MethodSource("validateStringListTestArguments")
    void validateStringListTest(List<String> testValue, boolean expectedResult) {
        // when
        Executable executable = () -> StringAddCalculator.validateStringList(testValue);

        // then
        if (expectedResult) {
            assertDoesNotThrow(executable);
            return;
        }

        assertThrows(IllegalArgumentException.class, executable);
    }
}
