import React from 'react';
import { TouchableOpacity, StyleSheet, Image, View } from 'react-native';
import MnText from './MnText';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faChevronRight } from '@fortawesome/free-solid-svg-icons';
import { colors } from '../constants';

const RestaurantTile = ({ item, onPressItem }) => {
    return (
        <TouchableOpacity style={styles.tile} onPress={onPressItem}>
            {item.image && <Image style={styles.image} source={item.image} />}
            <View style={styles.detailsView}>
                <MnText style={styles.title}>{item.name}</MnText>
                <MnText style={styles.subtitle}>{item.category}</MnText>
            </View>
            <FontAwesomeIcon
                icon={faChevronRight}
                size={20}
                color={colors.primary}
            />
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
        shadowOffset: { width: 0, height: 1 },
        shadowOpacity: 0.5,
        shadowRadius: 1,
        borderWidth: 1,
        borderColor: colors.lightGray,
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
        color: colors.primary,
    },
    image: {
        height: 50,
        width: 50,
        resizeMode: 'cover',
        borderRadius: 50,
        borderWidth: 2,
        borderColor: colors.primary,
    },
});

export default RestaurantTile;
