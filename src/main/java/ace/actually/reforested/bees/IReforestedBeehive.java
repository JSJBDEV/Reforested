package ace.actually.reforested.bees;

public interface IReforestedBeehive {
    String reforested$getQueenBeeType();
    void reforested$setQueenBeeType(String beeType);
    long reforested$nextBreedingCheck();
    void reforested$setNextBreedingCheck(long next);

}
