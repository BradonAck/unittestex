package animals.petstore.pet.types;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;

import java.math.BigDecimal;

public class Snake extends Pet implements PetImpl{

    private Breed b;
    private boolean venomous;

    public Snake(AnimalType a, Skin s, Gender g, Breed b){
        this(a, s, g, b, new BigDecimal(0));
    }

    public Snake(AnimalType a, Skin s, Gender g, Breed b, BigDecimal cost){
        this(a, s, g, b, cost, 0);
    }

    public Snake(AnimalType a, Skin s, Gender g, Breed b, BigDecimal cost, int petID){
        super(PetType.SNAKE, cost, g, petID);
        super.skinType = s;
        super.animalType = a;
        this.b = b;
        this.venomous = isVenomous(b);
    }

    private boolean isVenomous(Breed breed){
        return breed == Breed.COPPERHEAD || breed == Breed.CORAL;
    }

    public String snakeHypoallergenic(){
        return super.petHypoallergenic(this.skinType).replaceAll("pet", "snake");
    }

    public String speak(){
        String language;
        switch (this.animalType){
            case DOMESTIC:
                language = "The snake goes Hiss! Hiss!";
                break;
            case WILD:
                language = "The snake goes Sss! Sss!";
                break;
            default:
                language = "the snake goes " + super.getPetType().speak + "! " + super.getPetType().speak + "!";
                break;
        }
        return language;
    }

    public boolean isItVenomous(){
        return this.venomous;
    }

    @Override
    public Breed getBreed() {
        return this.b;
    }

    public AnimalType getAnimalType() {
        return this.animalType;
    }

    @Override
    public String toString(){
        return super.toString() +
            "The snake is " + this.getAnimalType() + "!\n" +
            "The breed is " + this.getBreed() + "!\n" +
            "The snake is " + (isItVenomous() ? "venomous" : "not venomous") + "!\n" +
            this.snakeHypoallergenic() + "!\n" + this.speak();
    }





}
