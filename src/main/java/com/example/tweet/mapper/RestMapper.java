package com.example.tweet.mapper;

public interface RestMapper<I, O> {
    /**Maps an input of type I into an output of type O
     *
     * @param input : I
     * @return output: O
     */
    O toDTO(I input);
    I toEntity(O input);
}
