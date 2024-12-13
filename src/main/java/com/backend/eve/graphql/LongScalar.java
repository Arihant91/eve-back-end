package com.backend.eve.graphql;
import graphql.language.IntValue;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.GraphQLScalarType;
import graphql.schema.Coercing;

public class LongScalar {
    public static final GraphQLScalarType GraphQLLong = GraphQLScalarType.newScalar()
            .name("Long")
            .description("A 64-bit integer.")
            .coercing(new Coercing<Long, Long>() {
                @Override
                public Long serialize(Object dataFetcherResult) {
                    return ((Number) dataFetcherResult).longValue();
                }

                @Override
                public Long parseValue(Object input) {
                    return Long.parseLong(input.toString());
                }

                @Override
                public Long parseLiteral(Object input) {
                    if (input instanceof IntValue) {
                        return ((IntValue) input).getValue().longValue();  // Convert to long
                    }
                    throw new CoercingParseLiteralException("Invalid input for Long scalar: " + input);
                }
            })
            .build();
}
