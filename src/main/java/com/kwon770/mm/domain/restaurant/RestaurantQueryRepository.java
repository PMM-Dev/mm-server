package com.kwon770.mm.domain.restaurant;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
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
    public Optional<Restaurant> findByMultipleConditions(String type, String price,
                                                         String location, String deliveryable) {
        List<Restaurant> targetRestaurants = queryFactory
                .selectFrom(QRestaurant.restaurant)
                .where(
                        eqType(type),
                        eqPrice(price),
                        eqLocation(location),
                        eqDevliveryable(deliveryable)
                )
                .orderBy(NumberExpression.random().asc())
                .limit(1)
                .fetch();
        if (targetRestaurants.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(targetRestaurants.get(0));
    }

    private BooleanExpression eqType(String _type) {
        if (StringUtils.isEmpty(_type)) {
            return null;
        }
        return QRestaurant.restaurant.type.eq(Type.valueOf(_type));
    }

    private BooleanExpression eqPrice(String _price) {
        if (StringUtils.isEmpty(_price)) {
            return null;
        }
        return QRestaurant.restaurant.price.eq(Price.valueOf(_price));
    }

    private BooleanExpression eqLocation(String _locaiton) {
        if (StringUtils.isEmpty(_locaiton)) {
            return null;
        }
        return QRestaurant.restaurant.location.eq(Location.valueOf(_locaiton));
    }

    private BooleanExpression eqDevliveryable(String _deliveryable) {
        if (StringUtils.isEmpty(_deliveryable)) {
            return null;
        }
        return QRestaurant.restaurant.deliveryable.eq(_deliveryable.equals("true") ? true : false);
    }
}
