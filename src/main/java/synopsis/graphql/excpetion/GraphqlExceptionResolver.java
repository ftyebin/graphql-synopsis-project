package synopsis.graphql.excpetion;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class GraphqlExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(@NotNull Throwable ex, @NotNull DataFetchingEnvironment env) {
        ErrorType errorType;

        if (ex instanceof ServerRequestException) {
            errorType = ErrorType.ExecutionAborted;
        } else if (ex instanceof JsonToObjectException || ex instanceof JsonPropertyException) {
            errorType = ErrorType.ValidationError;
        } else if (ex instanceof ResultDataNotFoundException || ex instanceof IllegalArgumentException) {
            errorType = ErrorType.DataFetchingException;
        } else {
            return null;
        }

        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }
}
