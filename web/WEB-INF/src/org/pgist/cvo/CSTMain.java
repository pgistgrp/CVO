package org.pgist.cvo;

import java.util.Set;

/**
 * <p>Description: This class is a main program that prompts a user to input
 * his/her concerns, tokenize several tags from his/her concerns and then gives
 * the user the opportunity to review/approve selected tags.</p>
 * @Jie
 * @version 1.0 March 14, 2006
 */
public class CSTMain {
    /* The Set preTags stores the initially prepared tags in the knowledgebase*/
    private Set preTags;
    /* This Set knownTags stores the slected tags that is already one of the prepared tags existing in the knowledge */
    private Set knownTags;
    /* This Set candidateTags stores the tags that CST chooses from an user's input*/
    private Set candidateTags;
    /* This Set finalTags stores the approved tags by the user. This finalTag Set also is offered to the user interface textbox made by Zhong and Guirong */
    private Set finalTags;

    /**
     * read database and initiate tags from the knowledge-base. save those
     * prepared tags into the preTags Set.
     */
    public void initTags() {

    }

    /**
     * This match method find whether there are preTags existing in an user's input. If yes, add the count of that Tag in the knowledgebase and delete it from the input.
     * @param userInput String
     * @param preTags Set
     */
    public void match(String userInput, Set preTags) {

    }

    /**
     * Jie: I have no idea on this method because I donot know the criteria of chosing a tag from an user' input. Need further explanation of this part of the requirement.
     * This chooseTags method tokenizes tags from an users' input and save the tags as candidateTags Set.
     * @param userInput String
     */
    public void chooseTags(String userInput) {

    }


    /**
     * This method provides the user the chance to review and approve what has been tokenized as tags from his/her input. The user can change the selected tags and summit. The system will save the result in finalTags Set
     * @param selectedTags Set
     */
    public void review(Set candidateTags) {
    }


    public static void main(String[] args) {
        CSTMain cstmain = new CSTMain();
    }
}
