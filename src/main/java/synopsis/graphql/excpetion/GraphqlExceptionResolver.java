package synopsis.graphql.excpetion;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class GraphqlExceptionResolver extends DataFetcherExceptionResolverAdapter {

    private final Map<Class<? extends Throwable>, ErrorType> exceptionToErrorTypeMap = Map.of(
            EuxpRequestException.class, ErrorType.ExecutionAborted,
            SmdRequestException.class, ErrorType.ExecutionAborted,
            ScsRequestException.class, ErrorType.ExecutionAborted,
            JsonToObjectException.class, ErrorType.ValidationError,
            JsonPropertyException.class, ErrorType.ValidationError,
            ResultDataNotFoundException.class, ErrorType.DataFetchingException,
            WebClientRequestException.class, ErrorType.ExecutionAborted,
            WebClientResponseException.class, ErrorType.ExecutionAborted,
            IllegalArgumentException.class, ErrorType.DataFetchingException
    );

    @Override
    protected List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env)  {
        GraphQLError graphQLError = Optional.ofNullable(exceptionToErrorTypeMap.get(ex.getClass()))
                .map(errorType -> buildGraphQLError(errorType, ex, env))
                .orElse(null);
        return (graphQLError != null ? Collections.singletonList(graphQLError) : Collections.emptyList());

    }


    private GraphQLError buildGraphQLError(ErrorType errorType, Throwable ex, DataFetchingEnvironment env) {
        GraphQLError graphQLError = GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .extensions(Map.of("Code", ex.getCause()))
                .build();

        logGraphQLError(graphQLError);

        return graphQLError;
    }

    private void logGraphQLError(GraphQLError graphQLError) {
        log.info("ErrorType : {}, Error Path : {} , Error Message: {}",
                graphQLError.getErrorType().toString(),
                graphQLError.getPath(),
                graphQLError.getMessage());
    }
}
