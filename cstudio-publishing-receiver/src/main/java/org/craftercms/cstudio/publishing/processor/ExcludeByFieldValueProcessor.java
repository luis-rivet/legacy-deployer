package org.craftercms.cstudio.publishing.processor;

import org.apache.commons.lang.StringUtils;
import org.craftercms.commons.lang.RegexUtils;
import org.craftercms.core.service.ContentStoreService;
import org.craftercms.core.service.Context;
import org.craftercms.core.service.Item;
import org.springframework.beans.factory.annotation.Required;

import java.util.Map;

/**
 * {@link PublishingProcessor} decorator that excludes files based on the value of a field.
 * @author joseross
 */
public class ExcludeByFieldValueProcessor extends AbstractExcludeProcessor {

    /**
     * Field to check.
     */
    protected String fieldName;

    /**
     * Values that should cause a file to be excluded.
     */
    protected String[] excludedValues;

    @Required
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Required
    public void setExcludedValues(String[] excludedValues) {
        this.excludedValues = excludedValues;
    }

    @Override
    protected boolean excludeFile(ContentStoreService contentStoreService, Context context, String file) {
        Item item = contentStoreService.getItem(context, file);
        if (item != null) {
            String fieldValue = item.queryDescriptorValue(fieldName);
            if (StringUtils.isNotEmpty(fieldValue)) {
                return RegexUtils.matchesAny(fieldValue, excludedValues);
            }
        }

        return false;
    }

    @Override
    public String getName() {
        return ExcludeByFieldValueProcessor.class.getSimpleName();
    }
}
