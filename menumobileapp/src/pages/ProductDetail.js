import React from 'react';
import { ScrollView, View, StyleSheet } from 'react-native';
import MnText from '../components/MnText';

const ProductDetail = () => {
    return (
        <ScrollView style={styles.container}>
            <View style={[styles.card, styles.header]}>
                <MnText>Product Name</MnText>
            </View>
            <View style={styles.card}>
                <MnText>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eu libero consequat, ullamcorper metus eget, vulputate lacus. Integer auctor vestibulum mi, ut laoreet tellus interdum sed. Nulla cursus dolor in tellus vestibulum, vel sollicitudin tellus auctor. Mauris in lobortis odio. Nunc eget consequat diam. Duis a elit at dui finibus semper. Vivamus et dui pretium, rhoncus ipsum vitae, imperdiet elit. Mauris eget blandit neque, placerat commodo justo. Praesent cursus elementum turpis sit amet ultricies. Donec et justo sit amet metus faucibus volutpat. Cras a gravida urna, vitae euismod diam. Phasellus efficitur nisl eu enim lacinia bibendum.</MnText>
            </View>
        </ScrollView>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    header: {
        marginTop: 15,
    },
    card: {
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
})

export default ProductDetail;