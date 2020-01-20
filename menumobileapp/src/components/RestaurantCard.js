import React from 'react';
import { TouchableOpacity, StyleSheet, Image, View } from 'react-native';
import MnText from './MnText';
import { colors } from '../constants';

const RestaurantCard = ({ item, onPressItem }) => {
    return (
        <TouchableOpacity style={styles.tile} onPress={onPressItem}>
            <View style={styles.imageContainer}>
                {item.image && <Image style={styles.image} source={item.image} />}
            </View>
            <View style={styles.detailsView}>
                <MnText style={styles.title}>{item.name}</MnText>
                <MnText style={styles.subtitle}>{item.category}</MnText>
            </View>
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    tile: {
        marginHorizontal: 15,
        marginVertical: 7,
        backgroundColor: '#FFFFFF',
        shadowColor: '#CCCCCC',
        shadowOffset: { width: 0, height: 1 },
        shadowOpacity: 0.5,
        shadowRadius: 1,
        borderWidth: 1,
        borderColor: colors.lightGray,
    },
    detailsView: {
        flex: 1,
        marginHorizontal: 10,
        marginVertical: 10,
    },
    title: {
        fontSize: 16,
    },
    subtitle: {
        fontSize: 12,
        color: colors.primary,
    },
    imageContainer: {
        height: 170,
        backgroundColor: colors.lightGray,
    },
    image: {
        height: 170,
        width: '100%',
        flex: 1,
        resizeMode: 'cover',
    },
});

export default RestaurantCard;
