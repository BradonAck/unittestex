package tests;

import animals.AnimalType;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Snake;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SnakeTests {

    private static Snake snake;

    @BeforeAll
    public static void createSnake(){
        snake = new Snake(AnimalType.DOMESTIC, Skin.SCALES, Gender.MALE, Breed.BALL_PYTHON);
    }


    @Test
    @Order(1)
    @DisplayName("Snake Domestic Type Test")
    public void testSnakeDomesticType(){
        assertEquals(AnimalType.DOMESTIC, snake.getAnimalType(), "Snake Type Expected[ " + AnimalType.DOMESTIC + "]; Actual Type[ " + snake.getAnimalType() + "]");
    }

    @Test
    @Order(2)
    @DisplayName("Snake Says Hiss Test")
    public void testSnakeSaysHiss(){
        assertEquals("The snake goes Hiss! Hiss!", snake.speak(), "I was expecting Hiss! Hiss!, but got " + snake.speak());
    }

    @Test
    @Order(3)
    @DisplayName("Snake Hypoallergenic Test")
    public void testSnakeHypoallergenic(){
        assertEquals("The snake is hyperallergenic", snake.snakeHypoallergenic(), "The snake should be hyperallergenic");
    }

    @Test
    @Order(4)
    @DisplayName("Snake Breed Test")
    public void testSnakeBreed(){
        assertEquals(Breed.BALL_PYTHON, snake.getBreed(), "Expected Ball Python breed! But got " + snake.getBreed());
    }

    @Test
    @Order(5)
    @DisplayName("Venomous Snake Test")
    public void testVenomousSnake(){
        Snake v = new Snake(AnimalType.WILD, Skin.SCALES, Gender.MALE, Breed.COPPERHEAD);
        assertTrue(v.isItVenomous(), "Copperheads should be venomous");
    }

    @Test
    @Order(6)
    @DisplayName("Non-Venomous Snake Test")
    public void testNonVenomousSnake(){
        assertFalse(snake.isItVenomous(), "Ball Pythons should not be venomous");
    }
}
