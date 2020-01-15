import React, { useEffect, useState } from 'react';
import { TouchableOpacity, FlatList, StyleSheet } from 'react-native';
import MnText from '../components/MnText';

const CategoryTile = ({ item, onPressItem }) => {
    return (
        <TouchableOpacity style={styles.tile} onPress={onPressItem}>
            <MnText style={styles.tileTitle}>{item.name}</MnText>
        </TouchableOpacity>
    );
};

const CategoryList = ({ navigation }) => {
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        const items = [];

        for (let i = 1; i <= 10; i++) {
            const item = {
                id: i,
                name: 'Category ' + i,
            };

            items.push(item);
        }

        setCategories(items);
    }, []);

    function onPressCategory(category) {
        navigation.navigate('ProductList', {
            category: category,
        });
    }

    return (
        <FlatList
            contentContainerStyle={styles.container}
            data={categories}
            renderItem={({ item }) => (
                <CategoryTile
                    item={item}
                    onPressItem={() => {
                        onPressCategory(item);
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

export default CategoryList;
