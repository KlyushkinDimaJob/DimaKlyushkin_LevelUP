package ru.levelp.at.homework6.tests.comments;

import org.testng.annotations.Test;
import ru.levelp.at.homework6.functions.GoRestEnum.ParametersComment;
import ru.levelp.at.homework6.functions.comments.CommentsAllGetFunctions;
import ru.levelp.at.homework6.providers.CommentsDataProvider;

public class CommentsAllGetTest extends CommentsAllGetFunctions {
    @Test(dataProvider = "commentsGetAllParamDataProvider", dataProviderClass = CommentsDataProvider.class,
          description = "Проверка успешного получения комментариев с различными параметрами",
          groups = {"hw6", "comments"})
    public void testGetParam(ParametersComment[] parameters) {
        checkGetAllComments(parameters);
    }
}
