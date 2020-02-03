import React from 'react';
import { StyleSheet, Text } from 'react-native';

const MnText = props => {
    function setFontFamily() {
        if (props.black) {
            return styles.black;
        } else if (props.bold) {
            return styles.bold;
        } else if (props.light) {
            return styles.light;
        }
        return styles.regular;
    }

    return <Text {...props} style={[setFontFamily(), props.style]} />;
};

const styles = StyleSheet.create({
    black: {
        fontFamily: 'OpenSans-ExtraBold',
    },
    bold: {
        fontFamily: 'OpenSans-Bold',
    },
    regular: {
        fontFamily: 'OpenSans-Regular',
    },
    light: {
        fontFamily: 'OpenSans-Light',
    },
});

export default MnText;
