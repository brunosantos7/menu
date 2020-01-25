import React from 'react';
import { TouchableOpacity, StyleSheet, Image, View } from 'react-native';
import MnText from './MnText';
import { colors } from '../constants';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faStar } from '@fortawesome/free-regular-svg-icons';

const RestaurantCard = ({ item, onPressItem }) => {
    return (
        <TouchableOpacity style={styles.tile} onPress={onPressItem} activeOpacity={0.9}>
            <View style={styles.imageContainer}>
                {item.image && <Image style={styles.image} source={item.image} />}
            </View>
            <View style={styles.detailsView}>
                <View>
                    <MnText bold style={styles.title}>{item.name}</MnText>
                    <MnText style={styles.subtitle}>{item.category}</MnText>
                </View>
                <FontAwesomeIcon icon={faStar} size={28} color={colors.white} />
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
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        width: '100%',
        bottom: 0,
        paddingHorizontal: 10,
        paddingVertical: 10,
        position: 'absolute',
        backgroundColor: 'rgba(0, 0, 0, 0.6)'
    },
    title: {
        fontSize: 16,
        color: colors.white,
    },
    subtitle: {
        fontSize: 12,
        color: colors.white,
    },
    imageContainer: {
        height: 230,
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
