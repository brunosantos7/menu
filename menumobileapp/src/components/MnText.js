import React from 'react';
import { StyleSheet, Text } from 'react-native';

const MnText = props => {
    function setFontFamily() {
        if (props.black) {
            return styles.black;
        } else if (props.bold) {
            return styles.bold;
        } else if (props.light) {
            return styles.light
        }
        return styles.regular;
    }

    return <Text {...props} style={[setFontFamily(), props.style]}></Text>;
};

const styles = StyleSheet.create({
    black: {
        fontFamily: 'Muli-Black',
    },
    bold: {
        fontFamily: 'Muli-Bold',
    },
    regular: {
        fontFamily: 'Muli',
    },
    light: {
        fontFamily: 'Muli-Light',
    },
});

export default MnText;
