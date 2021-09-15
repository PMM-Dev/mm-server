package com.kwon770.mm.domain.restaurant;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public RestaurantQueryRepository(JPAQueryFactory queryFactory) {
        super(Restaurant.class);
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<Restaurant> findAllByType(String type) {
        return queryFactory
                .selectFrom(QRestaurant.restaurant)
                .where(eqType(type))
                .fetch();
    }

    @Transactional
    public Optional<Restaurant> findByMultipleConditions(List<String> type, List<String> price,
                                                         List<String> location, Boolean deliverable) {
        List<Restaurant> targetRestaurants = queryFactory
                .selectFrom(QRestaurant.restaurant)
                .where(
                        eqTypes(type),
                        eqPrices(price),
                        eqLocations(location),
                        eqDeliverable(deliverable)
                )
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(1)
                .fetch();
        if (targetRestaurants.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(targetRestaurants.get(0));
    }

    private BooleanExpression eqType(String type) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        return QRestaurant.restaurant.type.eq(Type.valueOf(type));
    }

    private BooleanBuilder eqTypes(List<String> types) {
        if (types == null) {
            return null;
        }

        BooleanBuilder typesBuilder = new BooleanBuilder();
        for (String type : types) {
            typesBuilder.or(QRestaurant.restaurant.type.eq(Type.valueOf(type)));
        }
        return typesBuilder;
    }

    private BooleanBuilder eqPrices(List<String> prices) {
        if (prices == null) {
            return null;
        }

        BooleanBuilder pricesBuilder = new BooleanBuilder();
        for (String price : prices) {
            pricesBuilder.or(QRestaurant.restaurant.price.eq(Price.valueOf(price)));
        }
        return pricesBuilder;
    }

    private BooleanBuilder eqLocations(List<String> locaitons) {
        if (locaitons == null) {
            return null;
        }

        BooleanBuilder locationsBuilder = new BooleanBuilder();
        for (String location : locaitons) {
            locationsBuilder.or(QRestaurant.restaurant.location.eq(Location.valueOf(location)));
        }
        return locationsBuilder;
    }

    private BooleanExpression eqDeliverable(Boolean deliverable) {
        if (deliverable == null) {
            return null;
        }
        return QRestaurant.restaurant.deliverable.eq(deliverable);
    }
}
