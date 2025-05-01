package tests;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Cat;
import animals.petstore.pet.types.Dog;
import animals.petstore.pet.types.Snake;
import animals.petstore.store.DuplicatePetStoreRecordException;
import animals.petstore.store.PetNotFoundSaleException;
import animals.petstore.store.PetStore;
import number.Numbers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class PetStoreTest
{
    private static PetStore petStore;

    @BeforeEach
    public void loadThePetStoreInventory()
    {
        petStore = new PetStore();
        petStore.init();
    }

    @Test
    @DisplayName("Inventory Count Test")
    public void validateInventory()
    {
        assertEquals(5, petStore.getPetsForSale().size(),"Inventory counts are off!");
    }

    @Test
    @DisplayName("Print Inventory Test")
    public void printInventoryTest()
    {
        petStore.printInventory();
    }

    @Test
    @DisplayName("Sale of Poodle Remove Item Test")
    public void poodleSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;
        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);

        // Validation
        petStore.soldPetItem(poodle);
        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Expected inventory does not match actual");
    }

    @Test
    @DisplayName("Poodle Duplicate Record Exception Test")
    public void poodleDupRecordExceptionTest() {
        petStore.addPetInventoryItem(new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1));
        Dog poodle = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1);

        // Validation
        String expectedMessage = "Duplicate Dog record store id [1]";
        Exception exception = assertThrows(DuplicatePetStoreRecordException.class, () ->{
            petStore.soldPetItem(poodle);});
        assertEquals(expectedMessage, exception.getMessage(), "DuplicateRecordExceptionTest was NOT encountered!");

    }

    @Test
    @DisplayName("Sale of Sphynx Remove Item Test")
    public void sphynxSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Cat sphynx = new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"),2);
        Cat removedItem = (Cat) petStore.soldPetItem(sphynx);

        // Validation
        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Expected inventory does not match actual");
        assertEquals(sphynx.getPetStoreId(), removedItem.getPetStoreId(), "The cat items are identical");
    }

    /**
     * Limitations to test factory as it does not instantiate before all
     * @return list of {@link DynamicNode} that contains the test results
     * @throws DuplicatePetStoreRecordException if duplicate pet record is found
     * @throws PetNotFoundSaleException if pet is not found
     */



    //Start New Testing for HW 1

    @Test
    @DisplayName("Setting Pet Store ID Test")
    public void setPetStoreIdTest() {
        //create a cat to begin the testing with an initial pet store ID
        Cat cat = new Cat(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.UNKNOWN );

        //test the initial ID is the expected value
        assertEquals(0, cat.getPetStoreId(), "Expected initial ID is 0");

        //change the ID to an arbitrary test value
        int newID = 5;
        cat.setPetStoreId(newID);

        //test the new ID is the expected value
        assertEquals(newID, cat.getPetStoreId(), "Expected new ID is 5");
    }

    @Test
    @DisplayName("Unknown Type of Pet Test")
    public void unknownPetTypeTest() {
        Pet unknown = new Pet(PetType.BIRD, new BigDecimal("10.00"), Gender.MALE, 1) {
            @Override
            public String toString() {
                return "Unknown Pet";
            }
        };

        String expected = "Unknown Pet Type is unable to be sold";
        Exception e = assertThrows(PetNotFoundSaleException.class, () ->{
            petStore.soldPetItem(unknown);
        });
        assertEquals(expected, e.getMessage());
    }

    @Test
    @DisplayName("Initialize with Duplicate Pet Test")
    public void duplicatePetTest(){
        petStore = new PetStore();

        Dog dog2 = new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE, new BigDecimal("650.00"), 1);

        petStore.initAddDuplicateItem(dog2);

        assertEquals(6, petStore.getPetsForSale().size(), "Expected inventory does not match actual");

        assertTrue(petStore.getPetsForSale().stream()
                        .anyMatch(p -> p instanceof Dog &&
                                ((Dog)p).getBreed() == Breed.POODLE &&
                                p.getPetStoreId() == 1),
                "Duplicate dog should be in inventory");

        String expected = "Duplicate Dog record store id [1]";
        Exception e = assertThrows(DuplicatePetStoreRecordException.class, () ->{
            petStore.soldPetItem(dog2);
        });
        assertEquals(expected, e.getMessage());
    }

    @Test
    @DisplayName("Snake Not Found Exception Test")
    public void snakeNotFoundExceptionTest() {
        Snake snake = new Snake(AnimalType.DOMESTIC, Skin.SCALES, Gender.MALE, Breed.BALL_PYTHON);
        String expectedMessage = "The Pet is not part of the pet store!!";

        Exception exception = assertThrows(PetNotFoundSaleException.class, () -> {
            petStore.soldPetItem(snake);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    //End New Testing for HW 1

    @TestFactory
    @DisplayName("Sale of Sphynx Remove Item Test2")
    public Stream<DynamicNode> sphynxSoldTest2() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Cat sphynx = new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"),2);
        Cat removedItem = (Cat) petStore.soldPetItem(sphynx);

        // Validation
        List<DynamicNode> nodes = new ArrayList<>();
        List<DynamicTest> dynamicTests = Arrays.asList(
                dynamicTest("Inventory Check Size Test ", () -> assertEquals(inventorySize,
                        petStore.getPetsForSale().size())),
                dynamicTest("The cat objects match ", () -> assertEquals(sphynx.toString(),
                        removedItem.toString()))
                );
        nodes.add(dynamicContainer("Cat Item 2 Test", dynamicTests));//dynamicNode("", dynamicContainers);

        return nodes.stream();
    }

    /**
     * Example of parameterized test
     * @param number to be tested
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, -10, 128, Integer.MIN_VALUE}) // six numbers
    void isNumberEven(int number)
    {
        assertTrue(Numbers.isEven(number));
    }



}
