package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Stemmers.Lancaster;
import main.java.ie.atu.forge.Stemmers.Lovins;
import main.java.ie.atu.forge.Stemmers.Porter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class StemmingServiceTest {

    @InjectMocks
    private StemmingService stemmingService;

    @Test
    void porter_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Porter.class)) {
            // ARRANGE
            String[] dummyOutput = {"run", "walk"};
            // We must use any(String[].class) because your service passes the whole array
            mockedStatic.when(() -> Porter.stem(any(String[].class))).thenReturn(dummyOutput);

            // ACT
            String[] result = stemmingService.porter("running walking");

            // ASSERT
            assertThat(result).isSameAs(dummyOutput);
            mockedStatic.verify(() -> Porter.stem(any(String[].class)));
        }
    }

    @Test
    void lancaster_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Lancaster.class)) {
            // ARRANGE
            String[] dummyOutput = {"org", "man"};
            mockedStatic.when(() -> Lancaster.stem(any(String[].class))).thenReturn(dummyOutput);

            // ACT
            String[] result = stemmingService.lancaster("organization management");

            // ASSERT
            assertThat(result).isSameAs(dummyOutput);
            mockedStatic.verify(() -> Lancaster.stem(any(String.class)));
        }
    }

    @Test
    void lovins_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Lovins.class)) {
            // ARRANGE
            String[] dummyOutput = {"rel", "inform"};
            mockedStatic.when(() -> Lovins.stem(any(String[].class))).thenReturn(dummyOutput);

            // ACT
            String[] result = stemmingService.lovins("relational information");

            // ASSERT
            assertThat(result).isSameAs(dummyOutput);
            mockedStatic.verify(() -> Lovins.stem(any(String[].class)));
        }
    }
}