import React from 'react';
import { TouchableOpacity, StyleSheet, Image, View } from 'react-native';
import MnText from './MnText';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faChevronRight } from '@fortawesome/free-solid-svg-icons';
import { colors } from '../constants';

const RestaurantTile = ({ item, onPressItem }) => {
    return (
        <TouchableOpacity style={styles.tile} onPress={onPressItem}>
            <Image style={styles.image} source={item.image} />
            <View style={styles.detailsView}>
                <MnText style={styles.title}>{item.name}</MnText>
                <MnText style={styles.subtitle}>{item.category}</MnText>
            </View>
            <FontAwesomeIcon icon={faChevronRight} size={20} color={colors.orange} />
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
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
        alignItems: 'center',
        justifyContent: 'space-between',
        flexDirection: 'row',
    },
    detailsView: {
        flex: 1,
        marginLeft: 15,
    },
    title: {
        fontSize: 16,
    },
    subtitle: {
        fontSize: 12,
        color: colors.orange,
    },
    image: {
        height: 50,
        width: 50,
        resizeMode: 'cover',
        borderRadius: 50,
        borderWidth: 2,
        borderColor: colors.orange,
    }
});

export default RestaurantTile;