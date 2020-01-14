import React, { useEffect, useState } from 'react';
import { TouchableOpacity, FlatList, Text, StyleSheet } from 'react-native';

const RestaurantTile = ({ item, onPressItem }) => {
    return (
        <TouchableOpacity style={styles.tile} onPress={onPressItem}>
            <Text style={styles.tileTitle}>{item.name}</Text>
        </TouchableOpacity>
    );
};

const Home = ({ navigation }) => {
    const [restaurants, setRestaurants] = useState([]);

    useEffect(() => {
        const items = [];

        for (let i = 1; i <= 15; i++) {
            const item = {
                id: i,
                name: 'Restaurant ' + i,
            };

            items.push(item);
        }

        setRestaurants(items);
    }, []);

    function onPressRestaurant(restaurant) {
        navigation.navigate('CategoryList', {
            restaurant: restaurant,
        });
    }

    return (
        <FlatList
            contentContainerStyle={styles.container}
            data={restaurants}
            renderItem={({ item }) => (
                <RestaurantTile
                    item={item}
                    onPressItem={() => {
                        onPressRestaurant(item);
                    }}
                />
            )}
            keyExtractor={item => item.id.toString()}
        />
    );
};

const styles = StyleSheet.create({
    container: {
        paddingVertical: 10,
    },
    tile: {
        marginHorizontal: 15,
        marginVertical: 7,
        backgroundColor: '#FFFFFF',
        paddingVertical: 15,
        paddingHorizontal: 10,
        borderRadius: 3,
        shadowColor: '#CCCCCC',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.5,
        shadowRadius: 1,
    },
    tileTitle: {
        fontSize: 15
    }
});

export default Home;
