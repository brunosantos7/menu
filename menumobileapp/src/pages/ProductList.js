import React, { useEffect, useState } from 'react';
import { TouchableOpacity, FlatList, Text, StyleSheet } from 'react-native';

const ProductTile = ({ item, onPressItem }) => {
    return (
        <TouchableOpacity style={styles.tile} onPress={onPressItem}>
            <Text style={styles.tileTitle}>{item.name}</Text>
        </TouchableOpacity>
    );
};

const ProductList = ({ navigation }) => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        const items = [];

        for (let i = 1; i <= 5; i++) {
            const item = {
                id: i,
                name: 'Product ' + i,
            };

            items.push(item);
        }

        setProducts(items);
    }, []);

    function onPressProduct(product) {
        navigation.navigate('ProductDetail', {
            product: product,
        });
    }

    return (
        <FlatList
            contentContainerStyle={styles.container}
            data={products}
            renderItem={({ item }) => (
                <ProductTile
                    item={item}
                    onPressItem={() => {
                        onPressProduct(item);
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

export default ProductList;
