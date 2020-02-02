import React from 'react';
import { TouchableOpacity, FlatList, StyleSheet } from 'react-native';
import MnText from '../components/MnText';

const ProductTile = ({ item, onPressItem }) => {
    return (
        <TouchableOpacity style={styles.tile} onPress={onPressItem}>
            <MnText style={styles.tileTitle}>{item.name}</MnText>
        </TouchableOpacity>
    );
};

const ProductList = ({ products }) => {
    return (
        <FlatList
            contentContainerStyle={styles.container}
            data={products}
            renderItem={({ item }) => (
                <ProductTile
                    item={item}
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
